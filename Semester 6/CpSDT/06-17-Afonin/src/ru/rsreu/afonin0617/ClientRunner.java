package ru.rsreu.afonin0617;

import java.util.Scanner;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0617.initializer.EnterprisesInitializer;
import ru.rsreu.afonin0617.model.Enterprise;
import ru.rsreu.afonin0617.service.EnterpriseFileService;
import ru.rsreu.afonin0617.view.converter.EnterpriseToTableConverter;
import ru.rsreu.afonin0617.view.table.SmartTableRenderer;

public class ClientRunner {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {
	}

	public static void main(String[] args) {

		StringBuilder outputBuilder = new StringBuilder();

		Enterprise[] initialEnterprises = EnterprisesInitializer.createInitialEnterprises();

		EnterpriseFileService fileService = new EnterpriseFileService();

		String sourceFilePath = fileService.createSourceFileWithEnterpriseData(initialEnterprises);

		String backupFilePath = fileService.copySourceFileToBackup(sourceFilePath);

		String finalFilePath = ClientRunner.askUserAndMoveFile(fileService, sourceFilePath);

		Enterprise[] backupEnterprises = fileService.readEnterprisesFromFile(backupFilePath);

		Enterprise[] movedEnterprises = fileService.readEnterprisesFromFile(finalFilePath);

		String[] headers = { RESOURCER.getString("table.column.name"), RESOURCER.getString("table.column.city"),
				RESOURCER.getString("table.column.ownership") };

		outputBuilder.append(RESOURCER.getString("message.output.original"));
		outputBuilder.append(SmartTableRenderer.drawTable(headers,
				EnterpriseToTableConverter.convertEnterprisesToTableRows(initialEnterprises)));

		outputBuilder.append(RESOURCER.getString("message.output.backup"));
		outputBuilder.append(SmartTableRenderer.drawTable(headers,
				EnterpriseToTableConverter.convertEnterprisesToTableRows(backupEnterprises)));

		outputBuilder.append(RESOURCER.getString("message.output.moved"));
		outputBuilder.append(SmartTableRenderer.drawTable(headers,
				EnterpriseToTableConverter.convertEnterprisesToTableRows(movedEnterprises)));

		boolean isBackupEqual = EnterprisesComparator.areEnterprisesEqual(initialEnterprises, backupEnterprises);

		boolean isMovedEqual = EnterprisesComparator.areEnterprisesEqual(initialEnterprises, movedEnterprises);

		outputBuilder.append(RESOURCER.getString("message.output.compare.backup")).append(isBackupEqual);

		outputBuilder.append(RESOURCER.getString("message.output.compare.moved")).append(isMovedEqual);

		System.out.println(outputBuilder);
	}

	private static String askUserAndMoveFile(EnterpriseFileService fileService, String sourceFilePath) {

		Scanner scanner = new Scanner(System.in);

		System.out.println(RESOURCER.getString("message.move.question"));
		String userAnswer = scanner.nextLine();

		scanner.close();

		if (userAnswer.equals(RESOURCER.getString("confirmation"))) {
			return fileService.moveSourceFileToTargetDirectory(sourceFilePath);
		}

		return sourceFilePath;
	}
}