package su.nightexpress.excellentcrates.crate.effect.list;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import su.nexmedia.engine.utils.EffectUtil;
import su.nexmedia.engine.utils.LocationUtil;
import su.nightexpress.excellentcrates.crate.effect.AbstractCrateBlockEffect;
import su.nightexpress.excellentcrates.crate.effect.CrateEffectModel;

public class CrateEffectHelix extends AbstractCrateBlockEffect {

    public CrateEffectHelix() {
        super(CrateEffectModel.HELIX, 1L, 24);
    }

    @Override
    public void doStep(@NotNull Location loc2, @NotNull String particleName, @NotNull String particleData, int step) {
        Location loc = loc2.clone().add(0, 0.05D, 0);

        double n2 = 0.3141592653589793 * step;
        double n3 = step * 0.1 % 2.5;
        double n4 = 0.75;
        Location pointOnCircle = LocationUtil.getPointOnCircle(loc, true, n2, n4, n3);
        Location pointOnCircle2 = LocationUtil.getPointOnCircle(loc, true, n2 - 3.141592653589793, n4, n3);
        EffectUtil.playEffect(pointOnCircle, particleName, particleData, 0.0f, 0.0f, 0.0f, 0.0f, 1);
        EffectUtil.playEffect(pointOnCircle2, particleName, particleData, 0.0f, 0.0f, 0.0f, 0.0f, 1);
    }
}
