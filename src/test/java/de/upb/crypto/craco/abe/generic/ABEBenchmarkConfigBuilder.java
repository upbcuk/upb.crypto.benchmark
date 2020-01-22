package de.upb.crypto.craco.abe.generic;

import de.upb.crypto.craco.interfaces.CipherText;
import de.upb.crypto.craco.interfaces.DecryptionKey;
import de.upb.crypto.craco.interfaces.EncryptionKey;
import de.upb.crypto.craco.interfaces.PlainText;
import de.upb.crypto.craco.interfaces.abe.Attribute;
import de.upb.crypto.craco.interfaces.abe.BigIntegerAttribute;
import de.upb.crypto.craco.interfaces.abe.SetOfAttributes;
import de.upb.crypto.craco.interfaces.pe.PredicateEncryptionScheme;
import de.upb.crypto.craco.interfaces.policy.BooleanPolicy;
import de.upb.crypto.craco.interfaces.policy.Policy;
import de.upb.crypto.craco.interfaces.policy.ThresholdPolicy;

import java.util.Collection;
import java.util.Set;

public class ABEBenchmarkConfigBuilder {

    /**
     * The attribute sets to test with their names.
     */
    private SetOfAttributesNamePair[] setOfAttributesNamePairs;

    /**
     * The different policies to test with their names.
     */
    private PolicyNamePair[] policyNamePairs;

    /**
     * Whether the scheme being tested is a ciphertext-policy scheme. Makes a difference in the benchmark as
     * the defined policies and attribute sets will be used for encryption/decryption keys, depending on scheme type.
     */
    private boolean isCPABE;
    private boolean isCPABEInitialized;

    /**
     * How many runs through the benchmark process to perform for warming up the JVM.
     */
    private int numWarmupRuns;
    /**
     * How often to do setup, i.e. generate master secret and scheme itself.
     * For each setup done, {@link this#numKeyGenerations} are performed.
     * This way, multiple setups can be tested with multiple runs per setup.
     */
    private int numSetups;
    /**
     * How many times to do the key generation per set up scheme.
     */
    private int numKeyGenerations;
    /**
     * How often to run {@link PredicateEncryptionScheme#encrypt(PlainText, EncryptionKey)}
     * and {@link PredicateEncryptionScheme#decrypt(CipherText, DecryptionKey)} per generated key pair.
     */
    private int numEncDecCycles;

    /**
     * Whether this benchmark run should print execution times and status updates during benchmark.
     */
    private boolean printDetails;

    public ABEBenchmarkConfigBuilder() {
        setOfAttributesNamePairs = new SetOfAttributesNamePair[1];
        policyNamePairs = new PolicyNamePair[2];
        SetOfAttributes validAttributes = new SetOfAttributes();
        // 64 attributes as default
        // TODO: Add more attribute sets to default? (maybe exponentially increasing number)
        for (int i = 0; i < 64; i++) {
            BigIntegerAttribute att = new BigIntegerAttribute(i);
            validAttributes.add(att);
        }
        setOfAttributesNamePairs[0] = new SetOfAttributesNamePair(validAttributes, "64 BigInt");
        // default is full AND and full OR
        policyNamePairs[0] = new PolicyNamePair(
                new BooleanPolicy(BooleanPolicy.BooleanOperator.AND, validAttributes), "64 BigInt All AND"
        );
        policyNamePairs[1] = new PolicyNamePair(
                new BooleanPolicy(BooleanPolicy.BooleanOperator.OR, validAttributes), "64 BigInt All OR"
        );

        numWarmupRuns = 1;
        numSetups = 1;
        numKeyGenerations = 1;
        numEncDecCycles = 1;
        printDetails = true;
    }

    public ABEBenchmarkConfig buildConfig() {
        if (!isCPABEInitialized) {
            throw new IllegalArgumentException("You must set the type of scheme which this config is for, CP or KP.");
        }
        return new ABEBenchmarkConfig(setOfAttributesNamePairs, policyNamePairs, isCPABE, numSetups, numKeyGenerations,
                numEncDecCycles, numWarmupRuns, printDetails);
    }

    public ABEBenchmarkConfigBuilder setAttributesNamePairs(SetOfAttributesNamePair[] setOfAttributesNamePairs) {
        this.setOfAttributesNamePairs = setOfAttributesNamePairs;
        return this;
    }

    public ABEBenchmarkConfigBuilder setPolicyNamePairs(PolicyNamePair[] policyNamePairs) {
        this.policyNamePairs = policyNamePairs;
        return this;
    }

    public ABEBenchmarkConfigBuilder setIsCPABE(boolean isCPABE) {
        this.isCPABE = isCPABE;
        this.isCPABEInitialized = true;
        return this;
    }

    public ABEBenchmarkConfigBuilder setNumWarmupRuns(int numWarmupRuns) {
        this.numWarmupRuns = numWarmupRuns;
        return this;
    }

    public ABEBenchmarkConfigBuilder setNumSetups(int numSetups) {
        this.numSetups = numSetups;
        return this;
    }

    public ABEBenchmarkConfigBuilder setNumKeyGenerations(int numKeyGenerations) {
        this.numKeyGenerations = numKeyGenerations;
        return this;
    }

    public ABEBenchmarkConfigBuilder setNumEncDecCylces(int numEncDecCycles) {
        this.numEncDecCycles = numEncDecCycles;
        return this;
    }

    public ABEBenchmarkConfigBuilder setPrintDetails(boolean printDetails) {
        this.printDetails = printDetails;
        return this;
    }
}
