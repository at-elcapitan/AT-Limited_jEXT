package limitedjext;
;
import dev.schlaubi.lavakord.interop.jda.LavakordJDABuilder;
import dev.schlaubi.lavakord.jda.LJDA;
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

        LJDA lavajda = new LavakordJDABuilder(JDABuilder.createDefault(TOKEN)
                .addEventListeners(new BaseCommandsEventListener())
                //.addEventListeners(new MusicEventListener())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setActivity(Activity.playing("Link, start.."))
                .disableCache(CacheFlag.MEMBER_OVERRIDES)
                .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
                .setChunkingFilter(ChunkingFilter.NONE)
                .disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
                .setLargeThreshold(50))
                .build();

        JDA bot = lavajda.getJda();
        //LavaKord lvkord = lavajda.getLavakord();
        //JavaLavakord javalkord = JavaInterop.createJavaInterface(lvkord);
        //javalkord.addNode("http://localhost:2333", "formathenextweatherwillbefine");

        System.out.println(S_OK + "Bot configured");
        System.out.println("\t\tStage: Starting");
        System.out.println(S_OK + "Bot started");

        // Slash commands declare
        bot.updateCommands().addCommands(
                Commands.slash("clear", "command to clear chat  ")
                        .addOption(OptionType.INTEGER, "amount", "amount of messages to delete",
                                true),
                Commands.slash("inspect", "displays ENTIRE command list")
                            .addOption(OptionType.STRING, "command_name", "Enter command name",
                                false),
                Commands.slash("play", "play music from YouTube")
                        .addOption(OptionType.STRING, "url", "YouTube url to music (Not playlist)",
                                true),
                Commands.slash("disconnect", "Disconnect bot from voice channel")
        ).queue();
    }
}