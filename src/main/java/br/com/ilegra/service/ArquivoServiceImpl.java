package br.com.ilegra.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.ilegra.enums.DataTypeLineEnum;
import br.com.ilegra.models.Customer;
import br.com.ilegra.models.Item;
import br.com.ilegra.models.Sales;
import br.com.ilegra.models.Salesman;

@Service
public class ArquivoServiceImpl implements ArquivoService {

	@Value("${HOMEPATH.OUT}")
	private String homePathOut;
	@Value("${HOMEPATH.IN}")
	private String homePathIn;
	
	public static final String LINE_BREAK = " \r\n";
	
	@Override
	public void fileRead() {
		File folder = new File(homePathIn);
		File[] listOfFiles = folder.listFiles();
		Report report = new Report();

		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().contains(".dat")) {
				this.fillObjects(file, report);
			}
		}

		this.gerarRelatorio(report);
	}

	private Report fillObjects(File file, Report report) {
		try {
			Path path = Paths.get(file.getAbsolutePath());
			List<String> linhasArquivo = Files.readAllLines(path);
			for (String linha : linhasArquivo) {
				String[] lineSplit = linha.split("ç");

				switch (DataTypeLineEnum.getPorId(lineSplit[0])) {
				case SALESMAN:
					report.getSalesmans().add(new Salesman(lineSplit[1], lineSplit[2],
							BigDecimal.valueOf(Double.valueOf(lineSplit[3].trim()))));
					break;
				case CUSTOMER:
					report.getCustomers().add(new Customer(lineSplit[1], lineSplit[2], lineSplit[3]));
					break;
				case SALES:
					List<Item> itens = new ArrayList<>();
					Sales sales = new Sales(Long.valueOf(lineSplit[1]), itens, lineSplit[3], BigDecimal.ZERO);

					String[] lineDeepSplit = lineSplit[2].split(",");
					for (String value : lineDeepSplit) {
						String[] lineSplitItem = value.split("-");

						itens.add(new Item(Long.valueOf(lineSplitItem[0].replaceAll("[\\p{Ps}\\p{Pe}]", "")),
								Integer.valueOf(lineSplitItem[1]),
								BigDecimal.valueOf(Double.valueOf(lineSplitItem[2].replaceAll("[\\p{Ps}\\p{Pe}]", ""))),
								BigDecimal.ZERO));
					}

					sales.setItens(itens);
					report.getSales().add(sales);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return report;
	}

	private void gerarRelatorio(Report report) {
		report.getSales().stream().forEach(sale -> {
			sale.getItens().stream().map(this::getFillTotalItem).toList();
			sale.setTotalValueSaleItens(this.getTotalItens(sale.getItens()));
		});

		try {
			FileWriter file = new FileWriter(homePathOut + "/report.done.dat");
			PrintWriter fileWriter = new PrintWriter(file);
			fileWriter.print(this.builderFileReport(report));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Item getFillTotalItem(Item item) {
		BigDecimal resultItem = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
		item.setTotal(resultItem);
		return item;
	}

	private BigDecimal getTotalItens(List<Item> itens) {
		List<BigDecimal> totalPorItem = new ArrayList<>();
		itens.stream().forEach(item -> {
			totalPorItem.add(item.getTotal());
		});
		
		return totalPorItem.stream().map(item -> item).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private String builderFileReport(Report report) {
		Sales biggestSale = report.getSales().stream().max(Comparator.comparing(Sales::getTotalValueSaleItens))
				.orElseThrow(NoSuchElementException::new);

		Sales lowestSale = report.getSales().stream().min(Comparator.comparing(Sales::getTotalValueSaleItens))
				.orElseThrow(NoSuchElementException::new);

		StringBuilder sb = new StringBuilder("Report: \r\n");
		sb.append("• Amount of clients in the input file: " + report.getCustomers().size() + LINE_BREAK);
		sb.append("• Amount of salesman in the input file: " + report.getSalesmans().size() + LINE_BREAK);
		sb.append("• ID of the most expensive sale: " + biggestSale.getId() + LINE_BREAK);
		sb.append("• Worst salesman ever: " + lowestSale.getSalesman() + LINE_BREAK);

		return sb.toString();
	}

}
