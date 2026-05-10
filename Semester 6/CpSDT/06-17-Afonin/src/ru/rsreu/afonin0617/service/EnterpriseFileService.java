package ru.rsreu.afonin0617.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.prutzkow.projectlogger.ProjectLogger;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0617.model.Enterprise;
import ru.rsreu.afonin0617.model.OwnershipForm;

public class EnterpriseFileService {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private static final int ENTERPRISE_DATA_PARTS_COUNT = 3;

	private static final String SOURCE_DIRECTORY_NAME = RESOURCER.getString("files.folder.source.name");

	private static final String COPY_SUBDIRECTORY_NAME = RESOURCER.getString("files.folder.copy.name");

	private static final String MOVE_DIRECTORY_NAME = RESOURCER.getString("files.folder.move.name");

	private static final String DATA_FILE_NAME = RESOURCER.getString("files.file.data.name");

	private static final String BACKUP_FILE_EXTENSION = RESOURCER.getString("files.file.backup.extension");

	public String createSourceFileWithEnterpriseData(Enterprise[] enterprises) {

		try {

			Path sourceDirectoryPath = Paths.get(EnterpriseFileService.SOURCE_DIRECTORY_NAME);

			Path copyDirectoryPath = sourceDirectoryPath.resolve(EnterpriseFileService.COPY_SUBDIRECTORY_NAME);

			Path sourceFilePath = sourceDirectoryPath.resolve(EnterpriseFileService.DATA_FILE_NAME);

			Files.createDirectories(copyDirectoryPath);

			ProjectLogger.logger.log(Level.INFO, "Created directory: " + copyDirectoryPath.toAbsolutePath());

			this.writeEnterpriseArrayToFile(sourceFilePath, enterprises);

			ProjectLogger.logger.log(Level.INFO, "Created file: " + sourceFilePath.toAbsolutePath());

			return sourceFilePath.toString();

		} catch (InvalidPathException exception) {

			String message = RESOURCER.getString("error.directory.create.invalid_path")
					+ EnterpriseFileService.SOURCE_DIRECTORY_NAME + ". "
					+ RESOURCER.getString("error.directory.create.invalid_path.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (FileSystemException exception) {

			String message = RESOURCER.getString("error.directory.create.locked")
					+ EnterpriseFileService.SOURCE_DIRECTORY_NAME + ". "
					+ RESOURCER.getString("error.directory.create.locked.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.directory.create.io")
					+ EnterpriseFileService.SOURCE_DIRECTORY_NAME + ". "
					+ RESOURCER.getString("error.directory.create.io.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);
		}
	}

	public String copySourceFileToBackup(String sourceFilePath) {

		try {

			Path sourcePath = Paths.get(sourceFilePath);

			String backupFileName = this.buildBackupFileName(sourcePath);

			Path backupFilePath = sourcePath.getParent().resolve(EnterpriseFileService.COPY_SUBDIRECTORY_NAME)
					.resolve(backupFileName);

			this.copyFileToBackupLocation(sourcePath, backupFilePath);

			return backupFilePath.toString();

		} catch (NoSuchFileException exception) {

			String message = RESOURCER.getString("error.copy.no_such_file") + sourceFilePath + ". "
					+ RESOURCER.getString("error.copy.no_such_file.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (InvalidPathException exception) {

			String message = RESOURCER.getString("error.copy.invalid_path") + sourceFilePath + ". "
					+ RESOURCER.getString("error.copy.invalid_path.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (FileSystemException exception) {

			String message = RESOURCER.getString("error.copy.locked") + sourceFilePath + ". "
					+ RESOURCER.getString("error.copy.locked.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.copy.io") + sourceFilePath + ". "
					+ RESOURCER.getString("error.copy.io.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);
		}
	}

	public String moveSourceFileToTargetDirectory(String sourceFilePath) {

		try {

			Path sourcePath = Paths.get(sourceFilePath);

			Path targetDirectory = Paths.get(EnterpriseFileService.MOVE_DIRECTORY_NAME);

			Files.createDirectories(targetDirectory);

			ProjectLogger.logger.log(Level.INFO, "Created directory: " + targetDirectory.toAbsolutePath());

			Path targetFilePath = targetDirectory.resolve(sourcePath.getFileName());

			Files.move(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);

			ProjectLogger.logger.log(Level.INFO, "Moved file: " + targetFilePath.toAbsolutePath());

			return targetFilePath.toString();

		} catch (NoSuchFileException exception) {

			String message = RESOURCER.getString("error.move.no_such_file") + sourceFilePath + ". "
					+ RESOURCER.getString("error.move.no_such_file.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (InvalidPathException exception) {

			String message = RESOURCER.getString("error.move.invalid_path") + sourceFilePath + ". "
					+ RESOURCER.getString("error.move.invalid_path.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (FileSystemException exception) {

			String message = RESOURCER.getString("error.move.locked") + sourceFilePath + ". "
					+ RESOURCER.getString("error.move.locked.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.move.io") + sourceFilePath + ". "
					+ RESOURCER.getString("error.move.io.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);
		}
	}

	public Enterprise[] readEnterprisesFromFile(String filePath) {

		try {

			List<Enterprise> enterpriseList = new ArrayList<>();

			this.readEnterpriseFileLines(filePath, enterpriseList);

			return enterpriseList.toArray(new Enterprise[0]);

		} catch (NoSuchFileException exception) {

			String message = RESOURCER.getString("error.read.no_such_file") + filePath + ". "
					+ RESOURCER.getString("error.read.no_such_file.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (InvalidPathException exception) {

			String message = RESOURCER.getString("error.read.invalid_path") + filePath + ". "
					+ RESOURCER.getString("error.read.invalid_path.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (FileSystemException exception) {

			String message = RESOURCER.getString("error.read.locked") + filePath + ". "
					+ RESOURCER.getString("error.read.locked.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (IllegalArgumentException exception) {

			String message = RESOURCER.getString("error.read.invalid_data") + filePath + ". "
					+ RESOURCER.getString("error.read.invalid_data.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

			throw new RuntimeException(message);

		} catch (IOException exception) {

			String message = RESOURCER.getString("error.read.io") + filePath + ". "
					+ RESOURCER.getString("error.read.io.fix");

			ProjectLogger.logger.log(Level.SEVERE, message);

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

	private void copyFileToBackupLocation(Path source, Path target) throws IOException {

		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

		ProjectLogger.logger.log(Level.INFO, "Copied file: " + target.toAbsolutePath());
	}

	private void readEnterpriseFileLines(String filePath, List<Enterprise> enterpriseList) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

			String line;

			while ((line = reader.readLine()) != null) {

				enterpriseList.add(this.parseEnterpriseLine(line));
			}
		}
	}

	private Enterprise parseEnterpriseLine(String line) {

		String[] parts = line.split(";");

		if (parts.length != EnterpriseFileService.ENTERPRISE_DATA_PARTS_COUNT) {

			throw new IllegalArgumentException(RESOURCER.getString("error.read.invalid_data.internal"));
		}

		return new Enterprise(parts[0], parts[1], OwnershipForm.valueOf(parts[2]));
	}

	private String buildBackupFileName(Path sourceFilePath) {

		String originalFileName = sourceFilePath.getFileName().toString();

		int extensionSeparatorIndex = originalFileName.lastIndexOf('.');

		String fileNameWithoutExtension;

		if (extensionSeparatorIndex > 0) {

			fileNameWithoutExtension = originalFileName.substring(0, extensionSeparatorIndex);

		} else {

			fileNameWithoutExtension = originalFileName;
		}

		return fileNameWithoutExtension + "." + EnterpriseFileService.BACKUP_FILE_EXTENSION;
	}
}