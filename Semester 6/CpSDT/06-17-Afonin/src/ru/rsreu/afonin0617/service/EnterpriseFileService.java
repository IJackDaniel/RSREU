package ru.rsreu.afonin0617.service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;

import com.prutzkow.projectlogger.ProjectLogger;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0617.model.Enterprise;
import ru.rsreu.afonin0617.model.OwnershipForm;

public class EnterpriseFileService {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private static final String SOURCE_DIRECTORY_NAME = RESOURCER.getString("files.folder.source.name");

	private static final String COPY_SUBDIRECTORY_NAME = RESOURCER.getString("files.folder.copy.name");

	private static final String MOVE_DIRECTORY_NAME = RESOURCER.getString("files.folder.move.name");

	private static final String DATA_FILE_NAME = RESOURCER.getString("files.file.data.name");

	private static final String BACKUP_FILE_EXTENSION = RESOURCER.getString("files.file.backup.extension");

	public String createSourceFileWithEnterpriseData(Enterprise[] enterprises) {

		try {
			Path sourceDirectoryPath = Paths.get(EnterpriseFileService.SOURCE_DIRECTORY_NAME);
			Path copyDirectoryPath = sourceDirectoryPath.resolve(EnterpriseFileService.COPY_SUBDIRECTORY_NAME);

			Files.createDirectories(copyDirectoryPath);

			ProjectLogger.logger.log(Level.INFO, "Created directory: " + copyDirectoryPath.toAbsolutePath());

			Path sourceFilePath = sourceDirectoryPath.resolve(EnterpriseFileService.DATA_FILE_NAME);

			this.writeEnterpriseArrayToFile(sourceFilePath, enterprises);

			ProjectLogger.logger.log(Level.INFO, "Created file: " + sourceFilePath.toAbsolutePath());

			return sourceFilePath.toString();

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.file.write") + EnterpriseFileService.SOURCE_DIRECTORY_NAME;

			ProjectLogger.logger.log(Level.SEVERE, message, exception);

			throw new RuntimeException(message);
		}
	}

	public String copySourceFileToBackup(String sourceFilePath) {

		try {
			Path sourcePath = Paths.get(sourceFilePath);

			String backupFileName = this.buildBackupFileName(sourcePath);

			Path backupFilePath = sourcePath.getParent().resolve(EnterpriseFileService.COPY_SUBDIRECTORY_NAME)
					.resolve(backupFileName);

			this.copyFile(sourcePath, backupFilePath);

			return backupFilePath.toString();

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.file.copy") + sourceFilePath;

			ProjectLogger.logger.log(Level.SEVERE, message, exception);

			throw new RuntimeException(message);
		}
	}

	public String moveSourceFileToTargetDirectory(String sourceFilePath) {

		try {
			Path sourcePath = Paths.get(sourceFilePath);
			Path targetDirectory = Paths.get(EnterpriseFileService.MOVE_DIRECTORY_NAME);

			Files.createDirectories(targetDirectory);

			Path targetFilePath = targetDirectory.resolve(sourcePath.getFileName());

			Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);

			ProjectLogger.logger.log(Level.INFO, "Moved file: " + targetFilePath.toAbsolutePath());

			return targetFilePath.toString();

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.file.move") + sourceFilePath;

			ProjectLogger.logger.log(Level.SEVERE, message, exception);

			throw new RuntimeException(message);
		}
	}

	public Enterprise[] readEnterprisesFromFile(String filePath) {

		try {
			List<Enterprise> enterpriseList = new ArrayList<>();

			this.readEnterpriseFileLines(filePath, enterpriseList);

			return enterpriseList.toArray(new Enterprise[0]);

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.file.read") + filePath;

			ProjectLogger.logger.log(Level.SEVERE, message, exception);

			throw new RuntimeException(message);
		}
	}

	private void writeEnterpriseArrayToFile(Path filePath, Enterprise[] enterprises) throws IOException {

		try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

			for (Enterprise enterprise : enterprises) {
				writer.write(enterprise.toString());
				writer.newLine();
			}
		}
	}

	private void copyFile(Path source, Path target) throws IOException {

		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

		ProjectLogger.logger.log(Level.INFO, "Copied file: " + target.toAbsolutePath());
	}

	private void readEnterpriseFileLines(String filePath, List<Enterprise> list) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

			String line;

			while ((line = reader.readLine()) != null) {
				list.add(this.parseEnterpriseLine(line));
			}
		}
	}

	private Enterprise parseEnterpriseLine(String line) {

		String[] parts = line.split(";");

		return new Enterprise(parts[0], parts[1], OwnershipForm.valueOf(parts[2]));
	}

	private String buildBackupFileName(Path sourceFilePath) {

		String originalFileName = sourceFilePath.getFileName().toString();

		int dotIndex = originalFileName.lastIndexOf('.');

		String baseName = originalFileName;
		if (dotIndex > 0) {
			originalFileName.substring(0, dotIndex);
		}

		return baseName + "." + EnterpriseFileService.BACKUP_FILE_EXTENSION;
	}
}