package bootstrap.main;

import bootstrap.parsers.jdt.EclipseJDTASTParser;
import bootstrap.parsers.spoon.SpoonASTParser;
import clustering.warpping.linkage.strategy.impls.AverageLinkageStrategy;
import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;
import clustering.warpping.models.Cluster;
import clustering.warpping.process.builder.PartitionBuilder;
import clustering.warpping.process.strategy.impls.DefaultClusteringAlgorithm;
import clustering.warpping.process.strategy.interfaces.ClusteringAlgorithm;
import metier.graphs.coupling.CouplingGraph;
import metier.graphs.transformer.CouplingGraphTransformer;
import metier.graphs.normal.Graph;
import metier.strategy.jdt.EclipseJDTStaticCallGraphStrategy;
import metier.strategy.spoon.SpoonStaticCallGraphStrategy;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class ParserLauncher {

    public static final String QUIT = "X";

    public static String projectPath= "C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master";

    //public static String projectPath = "" ;//= "C:\\Users\\gm_be\\IdeaProjects\\chain_of_responsibility";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";

    public static void main(String[] args) throws IOException {

        //////////////////////////////////////////////////////////
        ParserLauncher main = new ParserLauncher();
        BufferedReader inputReader;
        try {
           inputReader = new BufferedReader(new InputStreamReader(System.in));
            /* if (args.length < 1)
                setTestProjectPath(inputReader);
            else
                verifyTestProjectPath(inputReader, args[0]);*/
            String userInput = "";

            do {
                main.menu();
                userInput = inputReader.readLine();
                Graph graph = main.processUserInput(userInput);

                CouplingGraphTransformer couplingGraphTransformer = new CouplingGraphTransformer(graph);

                couplingGraphTransformer.calculateMetrics();

                CouplingGraph couplingGraph = couplingGraphTransformer.getCouplingGraph();



                LinkageStrategy strategy =
                        new AverageLinkageStrategy();
                Frame f1 = new DendrogramFrame( createSampleCluster(strategy/*, couplingGraph.getClasses(), couplingGraph.generateProximityMatrix(
                        couplingGraph.getClasses()
                ))*/ ));
                f1.setSize(500, 400);
                f1.setLocation(100, 200);

                Thread.sleep(3000);

            } while(!userInput.equals(QUIT));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Graph processUserInput(String userInput) {

        Graph graph = null;


        try {
            switch(userInput) {
                case "1":
                    EclipseJDTASTParser parser = new EclipseJDTASTParser(projectPath, jrePath);
                    graph = EclipseJDTStaticCallGraphStrategy.getInstance(parser).createCallGraph();
                    return graph;

                case "2":
                    SpoonASTParser spoonASTParser = new SpoonASTParser( projectPath, jrePath);
                    graph = SpoonStaticCallGraphStrategy.getInstance(spoonASTParser).createCallGraph();
                    return graph;

                case QUIT:
                    System.out.println("Bye...");
                    return null;

                default:
                    System.err.println("Sorry, wrong input. Please try again.");
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    private void menu() {
        StringBuilder builder = new StringBuilder();
        builder.append("Static call graph :");
        builder.append("\n1. Eclipse JDT.");
        builder.append("\n2. Spoon.");
        builder.append("\n"+QUIT+". To quit.");
        System.out.println(builder);
    }

    private static Cluster createSampleCluster(LinkageStrategy strategy) {

        String[] names = new String[] { "Vehicule", "Car", "Bus", "Calculator", "Inspector", "Inspection" };

        double[][] distances = new double[][] {

                { 0, 1, 9, 7, 11,14 },
                { 1, 0, 4, 3, 8, 1  },
                { 9, 4, 0, 9, 2, 8  },
                { 7, 3, 9, 0, 6, 13 },
                { 11, 8, 2, 6, 0,1  },
                { 14, 1, 8, 13, 1,0  }
        };



        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();

        //String[] myArray = new String[names.size()];

        Cluster cluster = alg.executeClustering(distances, names/*names.toArray(myArray)*/, strategy);

        cluster.toConsole(0);

        System.out.println("Partitions ::: ");

        System.err.println(PartitionBuilder.getInstance().selection_cluster(cluster));

        System.out.println(":::::::");
        return cluster;
    }

    protected static void setTestProjectPath(BufferedReader inputReader)
            throws IOException {
        File projectFolder;

        System.out.println("Please provide the path to a java project's src/ folder: ");

        projectPath = inputReader.readLine();

        projectFolder = new File(projectPath);

        while (!projectFolder.exists() ) {
            System.err.println("Error: "+projectPath+
                    " either doesn't exist or isn't a java project src/ folder. "
                    + "Please try again: ");
            projectPath = inputReader.readLine();
            projectFolder = new File(projectPath);
        }
    }

    protected static void verifyTestProjectPath(BufferedReader inputReader,
                                                String userInput) throws IOException {
        File projectFolder = new File(userInput);

        // || !userInput.endsWith("src/") ;

        while (!projectFolder.exists() ) {
            System.err.println("Error: "+userInput+
                    " either doesn't exist or isn't a java project src/ folder. "
                    + "Please try again: ");
            userInput = inputReader.readLine();
            projectFolder = new File(userInput);
        }

        projectPath = userInput;
    }


}
