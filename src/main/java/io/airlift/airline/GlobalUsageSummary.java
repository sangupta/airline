package io.airlift.airline;

import static io.airlift.airline.UsageHelper.toUsage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import io.airlift.airline.guava.GuavaUtil;
import io.airlift.airline.model.CommandGroupMetadata;
import io.airlift.airline.model.CommandMetadata;
import io.airlift.airline.model.GlobalMetadata;
import io.airlift.airline.model.OptionMetadata;

public class GlobalUsageSummary
{
    private final int columnSize;

    public GlobalUsageSummary()
    {
        this(79);
    }

    public GlobalUsageSummary(int columnSize)
    {
        GuavaUtil.checkArgument(columnSize > 0, "columnSize must be greater than 0");
        this.columnSize = columnSize;
    }

    /**
     * Display the help on System.out.
     */
    public void usage(GlobalMetadata global)
    {
        StringBuilder stringBuilder = new StringBuilder();
        usage(global, stringBuilder);
        System.out.println(stringBuilder.toString());
    }

    /**
     * Store the help in the passed string builder.
     */
    public void usage(GlobalMetadata global, StringBuilder out)
    {
        usage(global, new UsagePrinter(out, columnSize));
    }

    public void usage(GlobalMetadata global, UsagePrinter out)
    {
        //
        // Usage
        //

        // build arguments
        List<String> commandArguments = new ArrayList<>();
        commandArguments.addAll(GuavaUtil.transform(global.getOptions(), new GuavaUtil.ValueChanger<OptionMetadata, String>()
        {
            public String apply(OptionMetadata option)
            {
                if (option.isHidden()) {
                    return null;
                }
                return toUsage(option);
            }
        }));
        out.newPrinterWithHangingIndent(8)
                .append("usage:")
                .append(global.getName())
                .appendWords(commandArguments)
                .append("<command> [<args>]")
                .newline()
                .newline();

        //
        // Common commands
        //

        Map<String, String> commands = new TreeMap<>();
        for (CommandMetadata commandMetadata : global.getDefaultGroupCommands()) {
            if (!commandMetadata.isHidden()) {
                commands.put(commandMetadata.getName(), commandMetadata.getDescription());
            }
        }
        for (CommandGroupMetadata commandGroupMetadata : global.getCommandGroups()) {
            commands.put(commandGroupMetadata.getName(), commandGroupMetadata.getDescription());
        }

        out.append("The most commonly used ").append(global.getName()).append(" commands are:").newline();
        out.newIndentedPrinter(4).appendTable(GuavaUtil.transform(commands.entrySet(), new GuavaUtil.ValueChanger<Entry<String, String>, Iterable<String>>()
        {
            public Iterable<String> apply(Entry<String, String> entry)
            {
                return GuavaUtil.arrayList(entry.getKey(), GuavaUtil.firstNonNull(entry.getValue(), ""));
            }
        }));
        out.newline();
        out.append("See").append("'" + global.getName()).append("help <command>' for more information on a specific command.").newline();
    }
}
