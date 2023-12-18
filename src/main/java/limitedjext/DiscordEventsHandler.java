package limitedjext;

import kotlin.collections.ArrayDeque;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscordEventsHandler extends ListenerAdapter {
    private final String WELCOME_CHANNEL;
    DiscordEventsHandler(String welcome_chan) {
        WELCOME_CHANNEL = welcome_chan;
    }


    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        EmbedBuilder embed = new EmbedBuilder();

        Role userRole = event.getGuild().getRoles().stream()
                        .filter(role -> role.getName().equals("User"))
                        .findFirst()
                        .orElse(null);

        Role botRole = event.getGuild().getRoles().stream()
                .filter(role -> role.getName().equals("Bots"))
                .findFirst()
                .orElse(null);

        embed.setColor(0xa31eff);
        embed.setTitle("Welcome!");
        embed.setDescription("User %s just landed to Limited server!"
                .formatted(event.getMember().getAsMention()));
        embed.setThumbnail(event.getMember().getAvatarUrl());

        event.getGuild().addRoleToMember(event.getMember(), userRole)
                .queue();
        event.getJDA().getTextChannelById(WELCOME_CHANNEL).sendMessageEmbeds(embed.build())
                .queue();
    }
}
