package ru.rsreu.afonin0609.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.prutzkow.projectlogger.ProjectLogger;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0609.model.RouteNumber;
import ru.rsreu.afonin0609.model.RouteTaxi;

public class FileService {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private static final String SOURCE_FOLDER = RESOURCER.getString("files.folder.source.name");
	private static final String COPY_FOLDER = RESOURCER.getString("files.folder.copy.name");
	private static final String MOVE_FOLDER = RESOURCER.getString("files.folder.move.name");
	private static final String FILE_NAME = RESOURCER.getString("files.file.data.name");
	private static final String BACKUP_EXTENSION = RESOURCER.getString("files.file.backup.extension");

	public String createSourceFile(RouteTaxi[] taxis) {
		try {
			Path sourceDirectory = Paths.get(FileService.SOURCE_FOLDER);
			Path copyDirectory = sourceDirectory.resolve(FileService.COPY_FOLDER);

			Files.createDirectories(copyDirectory);
			ProjectLogger.logger.log(Level.INFO, "Directory created: " + copyDirectory.toAbsolutePath());

			Path filePath = sourceDirectory.resolve(FileService.FILE_NAME);

			this.writeRouteTaxisToFile(filePath, taxis);

			ProjectLogger.logger.log(Level.INFO, "File created: " + filePath.toAbsolutePath());

			return filePath.toString();

		} catch (IOException exception) {
			String exceptionText = RESOURCER.getString("error.file.write") + FileService.SOURCE_FOLDER;

			ProjectLogger.logger.log(Level.SEVERE, exceptionText, exception);

			throw new RuntimeException(exceptionText);
		}
	}

	public String copyToBackup(String sourcePath) {
		try {
			Path source = Paths.get(sourcePath);
			Path backup = source.getParent().resolve(FileService.COPY_FOLDER)
					.resolve(source.getFileName().toString() + "." + FileService.BACKUP_EXTENSION);

			this.copyFile(source, backup);

			return backup.toString();

		} catch (IOException exception) {
			String exceptionText = RESOURCER.getString("error.file.copy") + sourcePath;

			ProjectLogger.logger.log(Level.SEVERE, exceptionText, exception);

			throw new RuntimeException(exceptionText);
		}
	}

	public String moveFile(String sourcePath) {
		try {
			Path source = Paths.get(sourcePath);
			Path moveDirectory = Paths.get(FileService.MOVE_FOLDER);

			Files.createDirectories(moveDirectory);
			ProjectLogger.logger.log(Level.INFO, "Directory created: " + moveDirectory.toAbsolutePath());

			Path target = moveDirectory.resolve(source.getFileName());

			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

			ProjectLogger.logger.log(Level.INFO, "File moved: " + target.toAbsolutePath());

			return target.toString();

		} catch (IOException exception) {
			String exceptionText = RESOURCER.getString("error.file.move") + sourcePath;

			ProjectLogger.logger.log(Level.SEVERE, exceptionText, exception);

			throw new RuntimeException(exceptionText);
		}
	}

	public RouteTaxi[] readFromFile(String path) {
		try {
			List<RouteTaxi> taxis = new ArrayList<>();

			this.readRouteTaxisFromFile(path, taxis);

			return taxis.toArray(new RouteTaxi[0]);

		} catch (IOException exception) {
			String exceptionText = RESOURCER.getString("error.file.read") + path;

			ProjectLogger.logger.log(Level.SEVERE, exceptionText, exception);

			throw new RuntimeException(exceptionText);
		}
	}

	private void writeRouteTaxisToFile(Path path, RouteTaxi[] taxis) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for (RouteTaxi taxi : taxis) {
				writer.write(taxi.toString());
				writer.newLine();
			}
		}
	}

	private void copyFile(Path source, Path target) throws IOException {
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

		ProjectLogger.logger.log(Level.INFO, "File copied: " + target.toAbsolutePath());
	}

	private void readRouteTaxisFromFile(String path, List<RouteTaxi> taxis) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {

			String line;

			while ((line = reader.readLine()) != null) {
				taxis.add(this.parseRouteTaxi(line));
			}
		}
	}

	private RouteTaxi parseRouteTaxi(String line) {
		String[] parts = line.split(";");

		return new RouteTaxi(parts[0], parts[1], RouteNumber.valueOf(parts[2]));
	}
}