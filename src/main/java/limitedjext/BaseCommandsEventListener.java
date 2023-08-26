package limitedjext;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Arrays;
import java.util.List;

class BaseCommandsEventListener extends ListenerAdapter {
    Embeds embeds = new Embeds();
    private static final String prefix = "sc.";
    private static List<String> getArguments(String baseString) {
        return Arrays.stream(baseString.split(" ")).toList();
    }

    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "clear" -> {
                MessageChannel channel = event.getChannel();
                channel.getIterableHistory()
                        .takeAsync(event.getOptions().get(0).getAsInt())
                        .thenAccept(channel::purgeMessages);
                event.replyEmbeds(embeds.successEmbed("Messages was deleted.").build())
                        .setEphemeral(true)
                        .queue();
            }
            case "inspect" -> {
                List<OptionMapping> command = event.getOptionsByName("command_name");

                if (command.isEmpty()) {
                    event.replyEmbeds(embeds.inspect().build())
                        .setEphemeral(true)
                        .queue();
                    break;
                }

                switch (command.get(0).toString()) {
                    case "clear" -> event.replyEmbeds(embeds.clear().build()).setEphemeral(true).queue();
                    default -> event.replyEmbeds(embeds.error("801", "Command not found")
                            .build())
                            .setEphemeral(true)
                            .queue();
                }
            }
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
            case "inspect" -> {
                if (command.size() == 1) {
                    event.getChannel().sendMessageEmbeds(embeds.inspect().build()).queue();
                    break;
                }

                switch (command.get(1)) {
                    case "clear" -> event.getChannel().sendMessageEmbeds(embeds.clear().build()).queue();
                    default -> event.getChannel().sendMessageEmbeds(embeds.error("801", "Command not found")
                            .build()).queue();
                }
            }

            case "clear" -> {
                if (!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                    event.getChannel().sendMessageEmbeds(embeds.error("871", "Operation not permitted")
                            .build())
                            .queue();
                }

                MessageChannel channel = event.getChannel();
                try {
                  channel.getIterableHistory()
                            .takeAsync(Integer.parseInt(command.get(1)))
                            .thenAccept(channel::purgeMessages);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    event.getChannel().sendMessage("Can not delete 0 messages").queue();
                }
            }
        }
    }
}
