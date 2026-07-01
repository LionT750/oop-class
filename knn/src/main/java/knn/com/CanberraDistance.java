package knn.com;

import org.tribuo.math.distance.*;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.oracle.labs.mlrg.olcut.provenance.ConfiguredObjectProvenance;
import com.oracle.labs.mlrg.olcut.provenance.impl.ConfiguredObjectProvenanceImpl;
import org.tribuo.math.la.SGDVector;
import org.tribuo.math.la.VectorTuple;
import org.tribuo.math.protos.DistanceProto;

import java.util.Iterator;

/**
 * Canberra distance.
 */
public final class CanberraDistance implements Distance {
    private static final long serialVersionUID = 1L;

    /**
     * Protobuf serialization version.
     */
    public static final int CURRENT_VERSION = 0;

    /**
     * Constructs a Canberra distance function.
     */
    public CanberraDistance() {}

    /**
     * Deserialization factory.
     */
    public static CanberraDistance deserializeFromProto(int version, String className, Any message) {
        if (version < 0 || version > CURRENT_VERSION) {
            throw new IllegalArgumentException(
                    "Unknown version " + version +
                    ", this class supports at most version " + CURRENT_VERSION);
        }
        if (message.getValue() != ByteString.EMPTY) {
            throw new IllegalArgumentException("Invalid proto");
        }
        return new CanberraDistance();
    }

    @Override
    public DistanceProto serialize() {
        DistanceProto.Builder proto = DistanceProto.newBuilder();
        proto.setClassName(this.getClass().getName());
        proto.setVersion(CURRENT_VERSION);
        return proto.build();
    }

    @Override
    public double computeDistance(SGDVector first, SGDVector second) {
        Iterator<VectorTuple> firstItr = first.iterator();
        Iterator<VectorTuple> secondItr = second.iterator();

        VectorTuple firstTuple = firstItr.hasNext() ? firstItr.next() : null;
        VectorTuple secondTuple = secondItr.hasNext() ? secondItr.next() : null;

        double distance = 0.0;

        while (firstTuple != null || secondTuple != null) {
            if (secondTuple == null || (firstTuple != null && firstTuple.index < secondTuple.index)) {

                distance += 1.0;
                firstTuple = firstItr.hasNext() ? firstItr.next() : null;

            } else if (firstTuple == null ||
                    secondTuple.index < firstTuple.index) {

                distance += 1.0;
                secondTuple = secondItr.hasNext() ? secondItr.next() : null;

            } else {

                double denominator =
                        Math.abs(firstTuple.value) + Math.abs(secondTuple.value);

                if (denominator != 0.0) {
                    distance += Math.abs(firstTuple.value - secondTuple.value)
                            / denominator;
                }

                firstTuple = firstItr.hasNext() ? firstItr.next() : null;
                secondTuple = secondItr.hasNext() ? secondItr.next() : null;
            }
        }

        return distance;
    }

    @Override
    public String toString() {
        return "CanberraDistance()";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CanberraDistance;
    }

    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public ConfiguredObjectProvenance getProvenance() {
        return new ConfiguredObjectProvenanceImpl(this, "Distance");
    }
}