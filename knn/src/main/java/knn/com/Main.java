package knn.com;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.tribuo.*;
import org.tribuo.data.columnar.*;
import org.tribuo.data.columnar.processors.field.*;
import org.tribuo.data.columnar.processors.response.*;
import org.tribuo.data.csv.CSVDataSource;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.math.distance.DistanceType;
import org.tribuo.math.neighbour.NeighboursQueryFactoryType;
import org.tribuo.classification.*;
import org.tribuo.classification.ensemble.VotingCombiner;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.common.nearest.KNNModel;
import org.tribuo.common.nearest.KNNTrainer;
import java.util.Map;




public class Main {

    public static void main(String[] args) throws Exception {
        var csvPath = Paths.get("src", "main", "resources", "tribuo_language_words.csv");
        var csvLines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
        
        // check if data was loaded properly
        csvLines.stream().limit(5).forEach(System.out::println);

        var textPipeline = new CharacterNGramPipeline(2);
        var fieldProcessors = new ArrayList<FieldProcessor>();

        fieldProcessors.add(new TextFieldProcessor("word",textPipeline));
        
        var responseProcessor = new FieldResponseProcessor<>("language","UNK",new LabelFactory());

        var rowProcessor = new RowProcessor.Builder<Label>()
                .setFieldProcessors(fieldProcessors)
                .build(responseProcessor);
        

        var csvSource = new CSVDataSource<Label>(csvPath,rowProcessor,true); 

        var dataSplitter = new TrainTestSplitter<>(csvSource,0.7,1L);

        var trainingDataset = new MutableDataset<>(dataSplitter.getTrain());
        var testingDataset = new MutableDataset<>(dataSplitter.getTest());

        Trainer<Label> trainer = new KNNTrainer<>(12, DistanceType.COSINE.getDistance(), 2, new VotingCombiner(), KNNModel.Backend.INNERTHREADPOOL, NeighboursQueryFactoryType.BRUTE_FORCE);

        Model<Label> knnModel = trainer.train(trainingDataset);

        var evaluator = new LabelEvaluator();

        var trainEvaluation = evaluator.evaluate(knnModel, trainingDataset);
        var testEvaluation = evaluator.evaluate(knnModel, testingDataset);

        System.out.println("=================================");
        System.out.println("        MODEL PERFORMANCE");
        System.out.println("=================================");

        System.out.printf("Training Accuracy: %.2f%%%n",
                trainEvaluation.accuracy() * 100);

        System.out.printf("Testing Accuracy:  %.2f%%%n",
                testEvaluation.accuracy() * 100);

        System.out.println();

        System.out.println("========== TEST DETAILS ==========");
        System.out.println(testEvaluation);

        System.out.println();

        System.out.println("======= CONFUSION MATRIX ========");
        System.out.println(testEvaluation.getConfusionMatrix());

        System.out.println("=================================");

        System.out.println("=================================");

        System.out.println("=================================");

        for (String word : List.of(
        "travesseiro",
        "geladeira",
        "sapato",
        "camisa",
        "bicicleta",
        "telefone",
        "computacao",
        "programador",
        "universitario",
        "informativo",

        "pillow",
        "refrigerator",
        "shoe",
        "shirt",
        "bicycle",
        "telephone",
        "developer",
        "algorithm",
        "keyboard",
        "quickly",
        "hoarder",
        "cabresto",
"chateau")) {

        var example = rowProcessor.generateExample(
                Map.of("word", word), false).get();

        var prediction = knnModel.predict(example);

        System.out.printf("%-15s -> %s%n",
                word, prediction.getOutput().getLabel());
}
        }
}