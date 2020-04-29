package abox;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

import java.io.*;
import java.util.Random;

public class abox {

    private static String CSV_FOLDER = "G:\\Documentos\\MasterDegree\\BDMA\\Classes\\UPC\\SDM\\Lab3\\GraphDB_Abox\\inputfile\\";
    private static String OUTPUT = "G:\\Documentos\\MasterDegree\\BDMA\\Classes\\UPC\\SDM\\Lab3\\GraphDB_Abox\\outputfile\\";
    private static String CSV_AUTHOR = CSV_FOLDER + "author_node.csv";
    private static String CSV_PAPER = CSV_FOLDER + "paper_node.csv";
    private static String CSV_CONFERENCE = CSV_FOLDER + "conference_node.csv";
    private static String CSV_JOURNAL = CSV_FOLDER + "journal_node.csv";
    private static String CSV_VOLUME = CSV_FOLDER + "volume_node.csv";
    private static String CSV_EDITION = CSV_FOLDER + "edition_node.csv";
    private static String CSV_REVIEW = CSV_FOLDER + "review_node.csv";
    private static String CSV_ORGAN = CSV_FOLDER + "org_node.csv";

    private static String BASE_URL  = "http://www.semanticweb.org/";
    private static String PROPERTY_URL   = BASE_URL + "property/";
    private static String RESOURCE_URL   = BASE_URL + "resource/";

    public static void createPaper() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_PAPER));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String title = row_data[1];
                String year = row_data[3];
                String urls = row_data[6];
                String publications = row_data[7];
                String presentations = row_data[8];
                String citations = row_data[9];

                String authorid = String.valueOf(Integer.parseInt(row_data[10]));

                String paperUri = RESOURCE_URL + "paper_" + id;
                Resource currentPaper = model.createResource(paperUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "title"), title)
                        .addProperty(model.createProperty(PROPERTY_URL + "yearPaper"), year)
                        .addProperty(model.createProperty(PROPERTY_URL + "urlPaper"), urls);

                if (!authorid.equals("")){
                    currentPaper.addProperty(model.createProperty(PROPERTY_URL+"correspondsto"),
                            model.createResource(RESOURCE_URL + "author_" + authorid
                            ));
                }

                if(!publications.equals("")){
                    String[] publicationslist = publications.split("[|]");
                    for(String publication:publicationslist){
                        publication = String.valueOf(Long.parseLong(publication));
                        currentPaper.addProperty(model.createProperty(PROPERTY_URL+"publishedin"),
                                model.createResource(RESOURCE_URL+ "volume_"  +publication
                                ));
                    }
                }

                if(!presentations.equals("")){
                    String[] presentationslist = presentations.split("[|]");
                    for(String presentation:presentationslist){
                        presentation = String.valueOf(Long.parseLong(presentation));
                        currentPaper.addProperty(model.createProperty(PROPERTY_URL+"presentedin"),
                                model.createResource(RESOURCE_URL+ "edition_" +presentation
                                ));
                    }
                }

                if(!citations.equals("")){
                    String[] citationslist = citations.split("[|]");
                    for(String citation:citationslist){
                        currentPaper.addProperty(model.createProperty(PROPERTY_URL+"cites"),
                                model.createResource(RESOURCE_URL+ "paper_" +citation
                                ));
                    }
                }

            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"paper.nt")), true), "NT");
    }

    public static void createConference() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_CONFERENCE));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String conferencename = row_data[1];
                String conferenceUri = RESOURCE_URL + "conference_" + id;
                Resource currentConference = model.createResource(conferenceUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "conferencename"), conferencename);
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"conference.nt")), true), "NT");
    }

    public static void createEdition() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_EDITION));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String yearedition = row_data[5];
                String cityedition = row_data[6];
                String conferenceURI = RESOURCE_URL+ "conference_" + String.valueOf(Long.parseLong(row_data[7]));

                String editionUri = RESOURCE_URL + "edition_" + id;
                Resource currentEdition = model.createResource(editionUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "yearedition"), yearedition)
                        .addProperty(model.createProperty(PROPERTY_URL + "cityedition"), cityedition)
                        .addProperty(model.createProperty(PROPERTY_URL + "belongstoconference"), model.createResource(conferenceURI));
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"edition.nt")), true), "NT");
    }



    public static void createJournal() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_JOURNAL));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String journalname = row_data[1];
                String journalUri = RESOURCE_URL + "journal_" + id;
                Resource currentCompany = model.createResource(journalUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "journalname"), journalname);
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"journal.nt")), true), "NT");
    }

    public static void createVolume() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_VOLUME));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[2];
                String yearvolume = row_data[0];
                String numbervolume = row_data[1];
                String journalURI = RESOURCE_URL+ "journal_" +row_data[3];

                String volumeUri = RESOURCE_URL + "volume_" + id;
                Resource currentVolume = model.createResource(volumeUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "yearvolume"), yearvolume)
                        .addProperty(model.createProperty(PROPERTY_URL + "numvolume"), numbervolume)
                        .addProperty(model.createProperty(PROPERTY_URL + "belongstojournal"), model.createResource(journalURI));
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"volume.nt")), true), "NT");
    }

    public static void createAuthor() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_AUTHOR));
        String line;

        String[] organizations = {"1","2","3"};

        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String name = row_data[1];
                String writes = row_data[2];
                String reviews = row_data[3];

                Random random = new Random();
                String org = organizations[random.nextInt(3)];

                String organizationUri = RESOURCE_URL+"org_" + org.replace(" ","_");

                String authorUri = RESOURCE_URL + "author_" + id;

                Resource currentAuthor = model.createResource(authorUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "authorname"), name)
                        .addProperty(model.createProperty(PROPERTY_URL+"belongsto"),model.createResource(organizationUri));

                if(!writes.equals("")){
                    String[] paperlist = writes.split("[|]");
                    for(String paper:paperlist){
                        currentAuthor.addProperty(model.createProperty(PROPERTY_URL+"writes"),
                                model.createResource(RESOURCE_URL+ "paper_" + paper
                                        ));
                    }
                }

                if(!reviews.equals("")){
                    String[] reviewlist = reviews.split("[|]");
                    for(String review:reviewlist){
                        currentAuthor.addProperty(model.createProperty(PROPERTY_URL+"reviews"),
                                model.createResource(RESOURCE_URL+ "review_" + review
                                ));
                    }
                }
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"author.nt")), true), "NT");
    }

    public static void createReview() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_REVIEW));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String comment = row_data[1];
                String decision = row_data[2];
                String paperlURI = RESOURCE_URL+ "paper_" +row_data[3];

                String reviewUri = RESOURCE_URL + "review_" + id;
                Resource currentReview = model.createResource(reviewUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "reviewcomment"), comment)
                        .addProperty(model.createProperty(PROPERTY_URL + "reviewdecision"), decision)
                        .addProperty(model.createProperty(PROPERTY_URL + "reviewspaper"), model.createResource(paperlURI));
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"review.nt")), true), "NT");
    }

    public static void createOrganization() throws IOException {
        Model model = ModelFactory.createDefaultModel();
        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_ORGAN));
        String line;
        line = csvReader.readLine();//First Line doesn't matter (columns names)
        while ((line = csvReader.readLine()) != null) {
            try {
                String[] row_data = line.split(";");
                String id = row_data[0];
                String name = row_data[1];
                String orgUri = RESOURCE_URL + "org_" + id;
                Resource currentOrg = model.createResource(orgUri)
                        .addProperty(model.createProperty(PROPERTY_URL + "name"), name);
            }
            catch(Exception e){

            }
        }
        csvReader.close();
        model.write(new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(OUTPUT+"organization.nt")), true), "NT");
    }

    public static void main(String[] args) throws Exception {

        createPaper();
        createAuthor();
        createConference();
        createEdition();
        createJournal();
        createVolume();
        createReview();
        createOrganization();
    }

}
