package com.qbros;

import com.qbros.model.Cache;
import com.qbros.model.Context;
import org.apache.commons.cli.*;

/**
 * This class is responsible to starting the main thread. Reading the command line arguments.
 * and base on the passed argument, initialize {@link Context} object.
 * Then
 */
public class ContextManager {

    private static final String OPT_ROOT = "r";
    private static final String OPT_DEBUG = "d";
    private static final String OPT_STRICT = "s";
    private static final String cmdLineSyntaxHint = "java -jar WordCounter.jar";
    private static Context context;

    public static void main(String[] args) {

        Options options = prepareOptions();
        try {
            initContextFromCommandLine(args, options);
            startingCoordinator();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            new HelpFormatter().printHelp(cmdLineSyntaxHint, options);
        }
    }

    public static Context getContext() {
        return context == null ? new Context() : context;
    }

    private static void startingCoordinator() {
        Coordinator coordinator = new Coordinator(new Cache(), ContextManager.getContext());
        coordinator.init();
    }

    /**
     * @return All the CLI options for this application
     */
    private static Options prepareOptions() {

        final String ROOT_DESC = "Path to root directory which contains the source files";
        final String STRICT_DESC = "String values which are only consist of " +
                "alphanumeric characters are considered word (Strict)";
        final String DEBUG_DESC = "Enables debug mode";

        final String rootPath = "rootPath";

        Option rootOpt = Option.builder(OPT_ROOT)
                .desc(ROOT_DESC)
                .argName(rootPath)
                .hasArg()
                .build();

        Option strictOpt = Option.builder(OPT_STRICT)
                .desc(STRICT_DESC)
                .build();

        Option debugOpt = Option.builder(OPT_DEBUG)
                .desc(DEBUG_DESC)
                .build();

        return new Options()
                .addOption(rootOpt)
                .addOption(strictOpt)
                .addOption(debugOpt);
    }

    /**
     * Sets the attributes of the {@link Context} based on the options passed in
     * via command line
     * @param args command line args
     * @param options command line options
     * @throws ParseException
     */
    private static void initContextFromCommandLine(String[] args, Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        context = new Context();
        if (commandLine.hasOption(OPT_ROOT)) {
            context.setRootPath(commandLine.getOptionValue(OPT_ROOT));
        }
        context.setStrict(commandLine.hasOption(OPT_STRICT));
        context.setDebug(commandLine.hasOption(OPT_DEBUG));
    }

}
