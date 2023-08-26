package limitedjext;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.File;

public class Main implements IColors {
    public static void main(String[] args) throws Exception {
        System.out.println("AT PROJECT Limited, 2023; ATLBj-260820231730");
        System.out.println("Product licensed by CC BY-NC-ND-4, file `LICENSE`");
        System.out.println("The license applies to all project files and previous versions (commits)");
        System.out.println("\t\tStage: Preload");

        File env = new File(".env");
        if (!env.exists()) throw new Exception(".env file is not exists.");
        System.out.println(S_OK + "Checked .env");

        Dotenv configLoad = Dotenv.load();
        String TOKEN = configLoad.get("TOKEN");

        if (TOKEN == null) {
            throw new Exception(".env file corrupt or misconfigured");
        }

        JDA bot = JDABuilder.createDefault(TOKEN)
                .addEventListeners(new BotEventListener())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setActivity(Activity.playing("Link, start.."))
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
                .setChunkingFilter(ChunkingFilter.NONE)
                .disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
                .setLargeThreshold(50)
                .build();
        System.out.println("\t\tStage: Starting");
        bot.awaitReady();
        System.out.println(S_OK + "Bot started");

        // Slash commands declare
        bot.updateCommands().addCommands(
                Commands.slash("hello", "Start command"),
                Commands.context(Command.Type.MESSAGE, "sc.inspect")
        ).queue();
    }
}