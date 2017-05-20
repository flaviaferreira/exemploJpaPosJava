package posjava.persistence.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTAMENTOS")
public class Departamento {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	
	private String nome;
	
	@OneToMany(mappedBy = "departamento")
	private Collection<Empregado> empregados;
	
	public Departamento() {
	}
	
	public Departamento(String nome) {
		this.nome = nome;
	}
	
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

	public Collection<Empregado> getEmpregados() {
		return empregados;
	}

	public void setEmpregados(Collection<Empregado> empregados) {
		this.empregados = empregados;
	}
	
}
