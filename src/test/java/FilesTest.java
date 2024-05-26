import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class FilesTest {

    private final ClassLoader cl = FilesTest.class.getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Проверяем содержимое CSV в ZIP архиве")
    void CSVinZipTest() throws Exception{
        try (InputStream stream = cl.getResourceAsStream("ArchiveTest.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("csv")){
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    assertThat(content)
                            .isNotEmpty()
                            .hasSize(3);
                    assertThat(content.get(1))
                            .isEqualTo(new String[]{"Engine", " S63"});
                }
            }
        }
    }

    @Test
    @DisplayName("Проверяем содержимое XLSX в ZIP архиве")
    void XLSXinZipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("ArchiveTest.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("xlsx")){
                    XLS xls = new XLS(zis);
                    final String heroSelection = xls.excel
                            .getSheetAt(0).getRow(0).getCell(0).getStringCellValue(),

                            heroCaption=xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue(),
                            pickrateCaption=xls.excel.getSheetAt(0).getRow(0).getCell(2).getStringCellValue(),
                            hero= xls.excel.getSheetAt(0).getRow(3).getCell(0).getStringCellValue(),
                            pickrate = xls.excel.getSheetAt(0).getRow(3).getCell(1).getStringCellValue();
                    final int coreCount = (int)
                            xls.excel.getSheetAt(0).getRow(3).getCell(2).getNumericCellValue();
                    final int sheetCount = xls.excel.getNumberOfSheets();
                    assertThat(heroSelection)
                            .isEqualTo("hero");
                    assertThat(heroCaption)
                            .isEqualTo("pickrate");
                    assertThat(pickrateCaption)
                            .isEqualTo("GPM");
                    assertThat(hero)
                            .isEqualTo("Underlord");
                    assertThat(pickrate)
                            .isEqualTo("Low");
                    assertThat(coreCount)
                            .isEqualTo(800);
                    assertThat(sheetCount)
                            .isEqualTo(1);
                }
            }
        }
    }

    @Test
    @DisplayName("Проверяем содержимое PDF в ZIP архиве")
    void PDFinZipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("ArchiveTest.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
             ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("pdf")){
                    PDF pdf = new PDF(zis);

                    assertThat(pdf.numberOfPages)
                            .isEqualTo(15);
                    assertThat(pdf)
                            .containsText("High-performance Gran Turismo");
                }
            }
        }
    }

    @Test
    @DisplayName("Проверяем содержимое JSON")
    void JsonValidationTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("Person.json");
            Reader reader = new InputStreamReader(stream)) {
            Person person = mapper.readValue(reader, Person.class);
            assertThat(person.getpersonOid().size())
                    .isEqualTo(4);
            assertThat(person.getOrganization().getLegal())
                    .isTrue();
            assertThat(person.getlastName())
                    .isEqualTo("Нечеловеческий");
            assertThat(person.getfirstName())
                    .isEqualTo("Порок");
            assertThat(person.getpersonOid())
                    .contains("10094586", "10094587", "10094588", "10094589");
        }
    }
}