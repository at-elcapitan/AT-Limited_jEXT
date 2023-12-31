package limitedjext;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.File;

public class Main implements IColors {
    public static void main(String[] args) throws Exception {
        System.out.println("AT PROJECT Limited, 2023; AT_jEXT-v0.0.2-beta");
        System.out.println("Product licensed by CC BY-NC-ND-4, file `LICENSE`");
        System.out.println("The license applies to all project files and previous versions (commits)");

        File env = new File(".env");
        if (!env.exists()) throw new Exception(".env file is not exists.");

        Dotenv configLoad = Dotenv.load();
        String TOKEN = configLoad.get("TOKEN");
        String WELCOME_CHANNEL = configLoad.get("WELCOME_CHANNEL");

        if (TOKEN == null | WELCOME_CHANNEL == null) {
            throw new Exception(".env file corrupt or misconfigured");
        }

        JDA bot = JDABuilder.createDefault(TOKEN)
                .addEventListeners(new CommandsEventHandler())
                .addEventListeners(new DiscordEventsHandler(WELCOME_CHANNEL))
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setActivity(Activity.playing("Link, start.."))
                .disableCache(CacheFlag.MEMBER_OVERRIDES)
                .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
                .setChunkingFilter(ChunkingFilter.NONE)
                .disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
                .setLargeThreshold(50)
                .build();

        // Slash commands declare
        bot.updateCommands().addCommands(
                Commands.slash("clear", "command to clear chat  ")
                        .addOption(OptionType.INTEGER, "amount", "amount of messages to delete",
                                true),
                Commands.slash("inspect", "displays ENTIRE command list")
                            .addOption(OptionType.STRING, "command_name", "Enter command name",
                                false),
                Commands.slash("openchat", "changes SEND MESSAGES privilege for @everyone"),
                Commands.slash("closechat", "changes SEND MESSAGES privilege for @everyone")
        ).queue();
    }
}