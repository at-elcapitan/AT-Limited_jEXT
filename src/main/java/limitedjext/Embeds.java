package limitedjext;

import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;

public class Embeds {
    private static final Color EMBED_COLOR = new Color(0xa31eff);

    public final EmbedBuilder successEmbed(String text) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("✅ Success");
        embed.setDescription(text);
        embed.setColor(Color.green);

        return embed;
    }

    public final EmbedBuilder inspect() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("ENTIRE command list");
        embed.setDescription("List of ALL system commands");
        embed.setColor(EMBED_COLOR);

        embed.addField("Global COMMANDS", "`inspect`", false);
        embed.addField("Work with chat", "`clear`, `closechat`, `openchat`", false);
        embed.addField("Work with users", "`ban`, `unban`", false);
        return embed;
    }

    public final EmbedBuilder error(String code, String text) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("System Alert: Code " + code);
        embed.setDescription(text);
        embed.setColor(Color.red);

        return embed;
    }

    public final EmbedBuilder clear() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("CLEAR command");
        embed.addField("sc.clear <value> | /clear <value>",
                "Work-with-chat command, using to clear chat." +
                "It's possible to delete up to 100 messages at a time `[From Chat-moderator]`", false);
        embed.setColor(EMBED_COLOR);

        return embed;
    }
}
