package ru.rsreu.afonin0617.view.table;

public enum TableSymbol {

	CORNER('+'), HORIZONTAL('-'), VERTICAL('|'), SPACE(' ');

	private final char symbol;

	TableSymbol(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return this.symbol;
	}
}