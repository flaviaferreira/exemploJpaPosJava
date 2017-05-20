package posjava.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmpregadoPropertyAccess {
	private Long   id;
	private String nome;
	private long ordenado;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getSalario() {
		return ordenado;
	}

	public void setSalario(long ordenado) {
		this.ordenado = ordenado;
	}
}
