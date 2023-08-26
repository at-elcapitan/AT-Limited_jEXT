package limitedjext;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MusicEventListener extends ListenerAdapter {
    byte loop = 0;
    int songPosition = 0;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "play" -> {
            }
        }
    }
}
