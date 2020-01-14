package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	// programa com comentarios por linha apenas para fixacao dos estudos para o
	// tema de trabalhando com arquivos

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();

		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine(); // diretorio completo do arquivo ex:
												// D:\\ws-Github\\working_with_files\\sf.csv

		File sourceFile = new File(sourceFileStr); // arquivo local sourceFile recebe o arquivo que esta em
													// sourceFileStr
		String sourceFolderStr = sourceFile.getParent(); // identifica para sourceFolderStr a pasta do arquivo
															// sourceFile

		boolean success = new File(sourceFolderStr + "\\out").mkdir(); // cria uma nova pasta em sourceFolderStr

		String targetFileStr = sourceFolderStr + "\\out\\summary.csv"; // cria um novo arquivo ou sobrepoe um arquivo
																		// existente na pasta

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) { // habilita leitura no arquivo de
																						// entrada

			String itemCsv = br.readLine(); // le a linha do arquivo
			while (itemCsv != null) { // enquanto houver uma linha com string faça

				String[] fields = itemCsv.split(","); // identifica os campos separados por ,
				String name = fields[0]; // identifica campo 0 Ex "TV"
				double price = Double.parseDouble(fields[1]); // identifica campo 1 Ex "1200.00"
				int quantity = Integer.parseInt(fields[2]); // identifica campo 2 Ex "2"

				list.add(new Product(name, price, quantity)); // adiciona os atributos splitados

				itemCsv = br.readLine(); // le a proxima linha enquanto itemCsv for diferente de null
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) { // habilita escrita no arquivo
																							// de saida

				for (Product item : list) { // para cada item Product na lista list
					bw.write(item.getName() + ", $" + String.format("%.2f", item.total())); // br.write escreva (nome e
																							// total)
					bw.newLine(); // nova linha para escrita
				}

				System.out.println(targetFileStr + " CREATED!");

			} catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		sc.close();
	}
}