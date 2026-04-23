package fr.kenda.speedcraft.hub.scheduler;

import fr.kenda.speedcraft.core.bossbar.BossbarService;
import fr.kenda.speedcraft.core.scheduler.Scheduler;
import fr.kenda.speedcraft.core.utils.Logger;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BossbarChangeTextScheduler extends Scheduler {

    record BossBarInfo(String text, BarColor color, BarStyle style) {
    }

    BossBarInfo lastInfo;
    private boolean filling = false;
    private final List<BossBarInfo> textToShow = Arrays.asList(
            new BossBarInfo("§cEn création...", BarColor.RED, BarStyle.SOLID),
            new BossBarInfo("§aRevenez bientôt...", BarColor.GREEN, BarStyle.SEGMENTED_10),
            new BossBarInfo("§6On prépare du lourd...", BarColor.YELLOW, BarStyle.SEGMENTED_10)
    );

    final BossbarService bossbarService;
    public BossbarChangeTextScheduler(int timer) {
        super(timer);
        bossbarService = BossbarService.getINSTANCE();
        getRandomBossbarInfo();
    }

    @Override
    public void run() {
        timer += filling ? 1 : -1;

        if (timer >= initTimer) {
            timer = initTimer;
            filling = false;
            getRandomBossbarInfo();
        } else if (timer <= 0) {
            timer = 0;
            filling = true;
            getRandomBossbarInfo();
        }

        bossbarService.setFilledBarNormalized((float) timer / (float) initTimer);
    }

    private void getRandomBossbarInfo() {
        if (textToShow.isEmpty()) return;

        BossBarInfo newInfo;
        do {
            int index = ThreadLocalRandom.current().nextInt(textToShow.size());
            newInfo = textToShow.get(index);
        } while (textToShow.size() > 1 && newInfo.equals(lastInfo));

        lastInfo = newInfo;

        bossbarService.updateTextBossBar(newInfo.text);
        bossbarService.updateStyleBar(newInfo.style);
        bossbarService.updateColorBar(newInfo.color);
    }
}