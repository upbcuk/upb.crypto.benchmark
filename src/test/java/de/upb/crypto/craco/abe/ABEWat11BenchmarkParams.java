package de.upb.crypto.craco.abe;

import de.upb.crypto.craco.abe.generic.ABEBenchmarkParams;
import de.upb.crypto.craco.abe.cp.large.ABECPWat11;
import de.upb.crypto.craco.abe.cp.large.ABECPWat11PublicParameters;
import de.upb.crypto.craco.abe.cp.large.ABECPWat11Setup;
import de.upb.crypto.craco.common.GroupElementPlainText;
import de.upb.crypto.craco.interfaces.PlainText;
import de.upb.crypto.craco.interfaces.abe.BigIntegerAttribute;
import de.upb.crypto.craco.interfaces.abe.SetOfAttributes;
import de.upb.crypto.craco.interfaces.pe.CiphertextIndex;
import de.upb.crypto.craco.interfaces.pe.KeyIndex;
import de.upb.crypto.craco.interfaces.policy.ThresholdPolicy;


/**
 * Benchmark parameters
 * for the large universe construction of {@link ABECPWat11}}
 * <p>
 * [Wat11] Brent Waters. Ciphertext-policy attribute-based encryption: An
 * expressive, efficient, and provably secure realization. In Public Key
 * Cryptography, pages 53–70. Springer, 2011
 *
 * @author Raphael Heitjohann, adapted from feidens' original benchmark
 */
public class ABEWat11BenchmarkParams extends ABEBenchmarkParams {

    private SetOfAttributes validAttributes;

    @Override
    public void doSetup(KeyIndex kIndex, CiphertextIndex cIndex) {
        ABECPWat11Setup cpSetup = new ABECPWat11Setup();
        cpSetup.doKeyGenRandomOracle(60);
        ABECPWat11PublicParameters pp = cpSetup.getPublicParameters();
        this.setScheme(new ABECPWat11(pp));
        this.setMasterSecret(cpSetup.getMasterSecret());
        this.setPublicParameters(pp);
    }

    @Override
    public PlainText generatePlainText() {
        return new GroupElementPlainText(
                ((ABECPWat11PublicParameters) this.getPublicParameters()).getGroupGT().getUniformlyRandomNonNeutral()
        );
    }
}
