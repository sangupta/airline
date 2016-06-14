package io.airlift.airline.model;

import io.airlift.airline.guava.GuavaUtil;

import java.util.List;

public class CommandGroupMetadata
{
    private final String name;
    private final String description;
    private final List<OptionMetadata> options;
    private final CommandMetadata defaultCommand;
    private final List<CommandMetadata> commands;

    public CommandGroupMetadata(String name, String description, Iterable<OptionMetadata> options, CommandMetadata defaultCommand, Iterable<CommandMetadata> commands)
    {
        this.name = name;
        this.description = description;
        this.options = GuavaUtil.immutableListOf(options);
        this.defaultCommand = defaultCommand;
        this.commands = GuavaUtil.immutableListOf(commands);
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public List<OptionMetadata> getOptions()
    {
        return options;
    }

    public CommandMetadata getDefaultCommand()
    {
        return defaultCommand;
    }

    public List<CommandMetadata> getCommands()
    {
        return commands;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("CommandGroupMetadata");
        sb.append("{name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", options=").append(options);
        sb.append(", defaultCommand=").append(defaultCommand);
        sb.append(", commands=").append(commands);
        sb.append('}');
        return sb.toString();
    }

    public static GuavaUtil.ValueChanger<CommandGroupMetadata, String> nameGetter()
    {
        return new GuavaUtil.ValueChanger<CommandGroupMetadata, String>()
        {
            public String apply(CommandGroupMetadata input)
            {
                return input.getName();
            }
        };
    }
}
