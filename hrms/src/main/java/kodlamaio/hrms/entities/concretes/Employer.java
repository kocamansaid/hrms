package kodlamaio.hrms.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employers")
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.JOINED)
public class Employer extends User {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	//private int id;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="web_address")
	private String website;
	
	@Column(name="phone_number")
	private String phoneNumber;
}
