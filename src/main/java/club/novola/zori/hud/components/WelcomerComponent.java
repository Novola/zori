package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.Welcomer;
import club.novola.zori.util.Wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WelcomerComponent extends HudComponent<Welcomer> {
    String name;
    private int timer;

    public WelcomerComponent() {
        super("Welcomer", 2, 2, Welcomer.INSTANCE);
    }

    @Override
    public void render() {
        if (Wrapper.getPlayer() != null && Wrapper.mc.world != null) {
            timer++;

            super.render();

            List<String> nameList = new ArrayList<>();
            nameList.add("Vaporimeter");// Random names from https://www.coolgenerator.com/minecraft-username-generator
            nameList.add("MaajLogjam");
            nameList.add("Cloying");
            nameList.add("MatrixjOr");
            nameList.add("Epiphany");
            nameList.add("Spinnbar");
            nameList.add("MethacrMadcap");
            nameList.add("Paraesthesia");
            nameList.add("Absquatulate");
            nameList.add("UncladJinker");
            nameList.add("Landlubber");
            nameList.add("Elytriferous");
            nameList.add("Shebang");
            nameList.add("ShihTzu");
            nameList.add("Piccadilly");
            nameList.add("Serpenticide");
            nameList.add("Decklechick3");
            nameList.add("Gasconade");
            nameList.add("Pettifogger");
            nameList.add("Petcock");
            nameList.add("Javanais");
            nameList.add("Squeegee");
            nameList.add("Pannikin");
            nameList.add("Gewgawperty05");
            nameList.add("Beeno1000");
            nameList.add("Cathedra");
            nameList.add("Swashbuckler");
            nameList.add("Oxymoron");
            nameList.add("Logotype");
            nameList.add("Frogman");
            nameList.add("Xenogenous");
            nameList.add("Acipenser");

            if(module.welcomeMode.getValue().equals(Welcomer.Mode.NORMAL)){
                name = Wrapper.getPlayer().getName();
            }else if(module.welcomeMode.getValue().equals(Welcomer.Mode.DOX)){
                name = System.getProperty("user.name");
            }else if(module.welcomeMode.getValue().equals(Welcomer.Mode.FAKENAME) && timer >= 600){
                name = getRandomName(nameList);
                timer = -150;
            }

            Wrapper.getFontRenderer().drawStringWithShadow("Welcome to Zori, " + name + " :^)", x, y, Zori.getInstance().clientSettings.getColor());
            width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
        }
    }

    public String getRandomName(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
