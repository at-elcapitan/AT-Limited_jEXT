package limitedjext;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;

class BotEventListener extends ListenerAdapter {
    Embeds embeds = new Embeds();
    private static final String prefix = "sc.";
    private static List<String> getArguments(String baseString) {
        return Arrays.stream(baseString.split(" ")).toList();
    }

    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "hello" -> event.reply("I'm alive").setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();

        if (!msg.contains(prefix)) {
            return;
        }
        List<String> command = getArguments(msg.replaceAll("sc.", ""));
        msg = command.get(0);

        switch (msg) {
            case "inspect" -> event.getChannel().sendMessageEmbeds(embeds.inspect().build()).queue();

            case "clear" -> {
                MessageHistory history = new MessageHistory(event.getChannel());
                try {
                    for (Message x : history.retrievePast(Integer.parseInt(command.get(1)) + 1).complete()) {
                        x.delete().queue();
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    event.getChannel().sendMessage("Can not delete 0 messages").queue();
                }
            }
        }
    }
}
