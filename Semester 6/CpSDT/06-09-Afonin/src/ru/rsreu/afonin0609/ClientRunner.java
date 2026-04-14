package ru.rsreu.afonin0609;

import java.util.Scanner;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0609.converter.RouteTaxiToTableConverter;
import ru.rsreu.afonin0609.initializer.RouteTaxiInitializer;
import ru.rsreu.afonin0609.model.RouteTaxi;
import ru.rsreu.afonin0609.service.FileService;
import ru.rsreu.afonin0609.view.TablePrinter;

public class ClientRunner {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {

	}

	public static void main(String[] args) {

		StringBuilder output = new StringBuilder();

		RouteTaxi[] taxis = RouteTaxiInitializer.initializeRouteTaxies();

		FileService fileService = new FileService();

		String sourcePath = fileService.createSourceFile(taxis);
		String backupPath = fileService.copyToBackup(sourcePath);
		String movedPath = ClientRunner.decideAndMoveFile(fileService, sourcePath);

		RouteTaxi[] backupTaxis = fileService.readFromFile(backupPath);
		RouteTaxi[] movedTaxis = fileService.readFromFile(movedPath);

		String[] headers = { RESOURCER.getString("table.column.registrerNumber"),
				RESOURCER.getString("table.column.owner"), RESOURCER.getString("table.column.route") };

		output.append(RESOURCER.getString("message.output.original"));
		output.append(TablePrinter.formTable(headers, RouteTaxiToTableConverter.convertRouteTaxiToTable(taxis)));

		output.append(RESOURCER.getString("message.output.backup"));
		output.append(TablePrinter.formTable(headers, RouteTaxiToTableConverter.convertRouteTaxiToTable(backupTaxis)));

		output.append(RESOURCER.getString("message.output.moved"));
		output.append(TablePrinter.formTable(headers, RouteTaxiToTableConverter.convertRouteTaxiToTable(movedTaxis)));

		boolean backupEquals = RouteTaxisComparator.areArraysEqual(taxis, backupTaxis);
		boolean movedEquals = RouteTaxisComparator.areArraysEqual(taxis, movedTaxis);

		output.append(RESOURCER.getString("message.output.compare.backup")).append(backupEquals);

		output.append(RESOURCER.getString("message.output.compare.moved")).append(movedEquals);

		System.out.println(output);
	}

	private static String readUserAnswer() {
		Scanner scanner = new Scanner(System.in);

		System.out.println(RESOURCER.getString("message.move.question"));
		String answer = scanner.nextLine();

		scanner.close();

		return answer;
	}

	private static boolean isConfirmed(String answer) {
		return answer.equals(RESOURCER.getString("confirmation"));
	}

	private static String decideAndMoveFile(FileService fileService, String sourcePath) {
		String answer = ClientRunner.readUserAnswer();

		boolean isMove = ClientRunner.isConfirmed(answer);

		if (isMove) {
			return fileService.moveFile(sourcePath);
		}

		return sourcePath;
	}
}
