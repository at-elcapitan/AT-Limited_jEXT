package limitedjext;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;

public class Embeds {
    public final EmbedBuilder inspect() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("ENTIRE COMMAND LIST");
        embed.setDescription("List of ALL system commands");
        embed.setColor(new Color(0xa31eff));

        embed.addField("Global COMMANDS", "`inspect`", false);
        embed.addField("Work with chat", "`clear`, `closechat`, `openchat`", false);
        embed.addField("Work with users", "`ban`, `unban`", false);
        return embed;
    }
}
