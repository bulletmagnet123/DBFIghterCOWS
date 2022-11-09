import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;


@ScriptManifest(author = "bulletmagnet", category = Category.COMBAT, description = "Fights cows", name = "Bullets Cow Fighter", version = 0.01)
public class FighterMain extends AbstractScript {
    Area CowArea = new Area(3264, 3256, 3254, 3296);


    @Override
    public void onStart() {

    }

    @Override
    public void onExit() {

    }


    public boolean HasFood() {
        if (Inventory.contains("Lobster")) {
            return true;
        } else {
            return false;
        }
    }

    public void BankGet () {
        print("Running bank method");
        if (HasFood() == false){
            Walking.walk(Bank.getClosestBankLocation());
            sleep(Calculations.random(650, 1220));
            Bank.open();
            sleep(Calculations.random(650, 1220));
            Bank.withdraw("Lobster", 21);
            sleep(Calculations.random(650, 1220));
            Bank.close();
            sleep(Calculations.random(650, 1220));

        } else {
            return;
        }
    }
    public void Attack() {
        if (CowArea.contains(Players.getLocal())) {
            NPC cow = NPCs.closest("Cow");
            sleep(470, 1070);
            if (HasFood()) {
                if (cow.exists() && !cow.isInCombat() && cow.canReach()) {
                    cow.interact("Attack");
                    sleep(Calculations.random(650, 1220));
                }
            } else {
                BankGet();
            }
        } else {
            Walking.walk(CowArea.getRandomTile());
            sleep(Calculations.random(650, 1220));
        }
    }

        @Override
        public int onLoop () {

            if (HasFood()) {
                Attack();
            } else {
                BankGet();
            }

            return Calculations.random(700, 1300);
        }
}
