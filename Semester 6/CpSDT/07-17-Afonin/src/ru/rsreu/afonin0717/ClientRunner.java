package ru.rsreu.afonin0717;

import ru.rsreu.afonin0717.model.Model;
import ru.rsreu.afonin0717.model.ModelResult;
import ru.rsreu.afonin0717.view.View;

public class ClientRunner {

	private ClientRunner() {
	}

	public static void main(String[] args) {

		Model model = new Model();

		ModelResult modelResult = model.execute();

		View view = new View(modelResult);

		view.show();

		String resultMessage = view.toStringType();

		System.out.println(resultMessage);
	}
}