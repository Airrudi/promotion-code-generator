package nl.ruudclaassen.jfall3.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Promotion;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//TODO: CR: replace @Component by @Repository
@Component
public class CodeFileDaoImpl implements CodeDao {

	@PersistenceContext
	private EntityManager em;

	private String buildFileName(int id) {
		return "code-" + id + ".txt";
	}

	@Override
	@Transactional
	public boolean save(List<Code> codes) {


		//em.persist(codes);
	return true;
	}

	// TODO: CR: throw a more specific custom exception
	public Set<String> load(Promotion Promotion) {
		String fileName = buildFileName(Promotion.getId());
		Set<String> codes = new HashSet<>();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(codes::add);
		} catch (IOException e) {
			throw new RuntimeException("File not found");
		}

		return codes;
	}

	// TODO: CR: don't use system.out.println
	// TODO: CR: don't catch java.lang.Exception
	// TODO: CR: do something when delete fails
	// TODO: CR: remove code that was commented out
	public void delete(Promotion Promotion) {

		try {
			String fileName = buildFileName(Promotion.getId());
			File file = new File(fileName);

			if (file.delete()) {
				// return true;
			} else {
				System.out.println("Delete operation is failed.");
				// return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
			// return false;
		}
	}
}
