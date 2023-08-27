package limitedjext

import dev.schlaubi.lavakord.interop.JavaLavakord
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

@Suppress("SENSELESS_COMPARISON")
class MusicEventListener() : ListenerAdapter() {
    private var embeds = Embeds()

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "play" -> {
                val uservoice = event.member!!.voiceState!!.channel!!.asVoiceChannel()

                if (uservoice == null) {
                    event.replyEmbeds(embeds.error("802", "You are not connected to voice channel.").build())
                            .setEphemeral(true)
                            .queue()
                }

                event.replyEmbeds(embeds.successEmbed("Connected to voice channel.").build())
                        .setEphemeral(true)
                        .queue()
            }

            "disconnect" -> {
                event.replyEmbeds(this.embeds.successEmbed("Disconnected from voice channel").build())
                        .setEphemeral(true)
                        .queue()
            }
        }
    }
}
