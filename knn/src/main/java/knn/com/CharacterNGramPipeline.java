package knn.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.labs.mlrg.olcut.provenance.ConfiguredObjectProvenance;
import com.oracle.labs.mlrg.olcut.provenance.impl.ConfiguredObjectProvenanceImpl;

import org.tribuo.Feature;
import org.tribuo.data.text.TextPipeline;

public class CharacterNGramPipeline implements TextPipeline {

    private final int n;

    public CharacterNGramPipeline(int n) {
        this.n = n;
    }

    @Override
    public List<Feature> process(String tag, String data) {

        Map<String,Double> counts = new HashMap<>();

        String word = "<" + data.toLowerCase() + ">";

        for (int i = 0; i <= word.length() - n; i++) {

            String gram = word.substring(i, i + n);

            String featureName = tag + "-" + gram;

            counts.merge(featureName, 1.0, Double::sum);
        }

        List<Feature> features = new ArrayList<>();

        for (var entry : counts.entrySet()) {
            features.add(
                new Feature(entry.getKey(), entry.getValue())
            );
        }

        return features;
    }

    @Override
    public ConfiguredObjectProvenance getProvenance() {
        return new ConfiguredObjectProvenanceImpl(this, "TextPipeline");
    }
}