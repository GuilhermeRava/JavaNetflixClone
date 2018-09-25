package JUNIT;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description JUnit to test the recent watched programs iterator
 */
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Users.*;
import Programs.*;
import Management.*;

class JUnit {
	// private List<Program> recentPrograms;
	static Management mg;

	@BeforeAll
	static void setup() {
		mg = new ManagementClass();
		String[] castMembers = new String[3];
		castMembers[0] = "marco";
		castMembers[1] = "joao";
		castMembers[2] = "Pedro";
		mg.upload("batata divertida", "Lula", 120, 7, 1999, "horror", castMembers, 12, "Series");
		mg.upload("arroz vinganca", "dilma", 150, 11, 2006, "acao", castMembers, 0, "Film");
		mg.upload("Adeus Portugal", "Socrates", 120, 14, 2007, "horror", castMembers, 0, "Film");
		mg.upload("a matanca", "PC", 4, 15, 2019, "aventura", castMembers, 4, "Series");
		mg.upload("vinganca", "PC", 4, 18, 2019, "aventura", castMembers, 4, "Series"); // filme adulto
		mg.upload("jogos mortais", "PC", 4, 18, 2019, "aventura", castMembers, 4, "Series"); //filme adulto
		mg.registAccount("joao", "joao123@someemail.com", "hello", "dep1");
	}

	@Test
	void correctAgeRatingCHILDRENProfile() {
		mg.createUserProfile("children", 15); //faixa etaria perfil maximo: 15
		mg.watchProgram("Adeus Portugal"); // faixa etaria programa: 14
		assertEquals(1, mg.getCurrentUserProfile().watchedProgramsCounter());
		mg.watchProgram("arroz vinganca"); // // faixa etaria programa: 11
		assertEquals(2, mg.getCurrentUserProfile().watchedProgramsCounter());
		mg.watchProgram("batata divertida"); //// faixa etaria programa: 7
		assertEquals(3, mg.getCurrentUserProfile().watchedProgramsCounter());
		mg.watchProgram("a matanca"); //// faixa etaria programa: 15, limite da faixa etaria do perfil
		assertEquals(4, mg.getCurrentUserProfile().watchedProgramsCounter());
	}
	
	@Test
	void correctAgeRatingNORMALProfile() {
		mg.createUserProfile("Adulto"); //pode assistir qualquer programa
		mg.watchProgram("vinganca"); //filme adulto, faixa etaria : 18
		assertEquals(1, mg.getCurrentUserProfile().watchedProgramsCounter());
		mg.watchProgram("jogos mortais"); //filme adulto, faixa etaria : 18
		assertEquals(2, mg.getCurrentUserProfile().watchedProgramsCounter());
		mg.watchProgram("batata divertida"); //filme crianca, faixa etaria: 7
		assertEquals(3, mg.getCurrentUserProfile().watchedProgramsCounter());
	}

	
}
