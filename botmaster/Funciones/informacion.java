public class informacion {
	private int contadorbots=0;

	public void addbot() {
		this.contadorbots++;
	}

	public void eliminarbot() {
		this.contadorbots--;
	}

	public int getbots() {
		return this.contadorbots;
	}
}
