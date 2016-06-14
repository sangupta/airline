package io.airlift.airline;

import io.airlift.airline.guava.GuavaUtil;
import io.airlift.airline.guava.SimpleMultiMap;
import io.airlift.airline.model.CommandGroupMetadata;
import io.airlift.airline.model.CommandMetadata;
import io.airlift.airline.model.OptionMetadata;

import java.util.List;

public class ParseState
{
    private final List<Context> locationStack;
    private final CommandGroupMetadata group;
    private final CommandMetadata command;
    private final SimpleMultiMap<OptionMetadata, Object> parsedOptions;
    private final List<Object> parsedArguments;
    private final OptionMetadata currentOption;
    private final List<String> unparsedInput;

    ParseState(CommandGroupMetadata group,
            CommandMetadata command,
            SimpleMultiMap<OptionMetadata, Object> parsedOptions,
            List<Context> locationStack,
            List<Object> parsedArguments,
            OptionMetadata currentOption,
            List<String> unparsedInput)
    {
        this.group = group;
        this.command = command;
        this.parsedOptions = parsedOptions;
        this.locationStack = locationStack;
        this.parsedArguments = parsedArguments;
        this.currentOption = currentOption;
        this.unparsedInput = unparsedInput;
    }

    public static ParseState newInstance()
    {
        return new ParseState(null, null, new SimpleMultiMap<>(), ImmutableList.<Context>of(), ImmutableList.of(), null, ImmutableList.<String>of());
    }

    public ParseState pushContext(Context location)
    {
        List<Context> locationStack = GuavaUtil.concatList(this.locationStack, location);
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState popContext()
    {
        List<Context> locationStack = GuavaUtil.immutableListOf(this.locationStack.subList(0, this.locationStack.size() - 1));
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState withOptionValue(OptionMetadata option, Object value)
    {
        SimpleMultiMap<OptionMetadata, Object> newOptions = new SimpleMultiMap<>();
        newOptions.putAll(parsedOptions);
        newOptions.put(option, value);

        return new ParseState(group, command, newOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState withGroup(CommandGroupMetadata group)
    {
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState withCommand(CommandMetadata command)
    {
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState withOption(OptionMetadata option)
    {
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, option, unparsedInput);
    }

    public ParseState withArgument(Object argument)
    {
        List<Object> newArguments = GuavaUtil.concatList(parsedArguments, argument);
        return new ParseState(group, command, parsedOptions, locationStack, newArguments, currentOption, unparsedInput);
    }

    public ParseState withUnparsedInput(String input)
    {
        List<String> newUnparsedInput = GuavaUtil.concatList(unparsedInput, input);
        return new ParseState(group, command, parsedOptions, locationStack, parsedArguments, currentOption, newUnparsedInput);
    }

    @Override
    public String toString()
    {
        return "ParseState{" +
                "locationStack=" + locationStack +
                ", group=" + group +
                ", command=" + command +
                ", parsedOptions=" + parsedOptions +
                ", parsedArguments=" + parsedArguments +
                ", currentOption=" + currentOption +
                ", unparsedInput=" + unparsedInput +
                '}';
    }

    public Context getLocation()
    {
        return locationStack.get(locationStack.size() - 1);
    }

    public CommandGroupMetadata getGroup()
    {
        return group;
    }

    public CommandMetadata getCommand()
    {
        return command;
    }

    public OptionMetadata getCurrentOption()
    {
        return currentOption;
    }

    public SimpleMultiMap<OptionMetadata, Object> getParsedOptions()
    {
        return parsedOptions;
    }

    public List<Object> getParsedArguments()
    {
        return parsedArguments;
    }

    public List<String> getUnparsedInput()
    {
        return unparsedInput;
    }
}
