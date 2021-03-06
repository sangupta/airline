package io.airlift.airline.model;

import io.airlift.airline.Accessor;
import io.airlift.airline.guava.GuavaUtil;

import java.util.List;

public class CommandMetadata
{
    private final String name;
    private final String description;
    private final boolean hidden;
    private final List<OptionMetadata> globalOptions;
    private final List<OptionMetadata> groupOptions;
    private final List<OptionMetadata> commandOptions;
    private final ArgumentsMetadata arguments;
    private final List<Accessor> metadataInjections;
    private final Class<?> type;

    public CommandMetadata(String name,
            String description,
            boolean hidden, Iterable<OptionMetadata> globalOptions,
            Iterable<OptionMetadata> groupOptions,
            Iterable<OptionMetadata> commandOptions,
            ArgumentsMetadata arguments,
            Iterable<Accessor> metadataInjections,
            Class<?> type)
    {
        this.name = name;
        this.description = description;
        this.hidden = hidden;
        this.globalOptions = GuavaUtil.immutableListOf(globalOptions);
        this.groupOptions = GuavaUtil.immutableListOf(groupOptions);
        this.commandOptions = GuavaUtil.immutableListOf(commandOptions);
        this.arguments = arguments;
        this.metadataInjections = GuavaUtil.immutableListOf(metadataInjections);
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public List<OptionMetadata> getAllOptions()
    {
        return ImmutableList.<OptionMetadata>builder().addAll(globalOptions).addAll(groupOptions).addAll(commandOptions).build();
    }

    public List<OptionMetadata> getGlobalOptions()
    {
        return globalOptions;
    }

    public List<OptionMetadata> getGroupOptions()
    {
        return groupOptions;
    }

    public List<OptionMetadata> getCommandOptions()
    {
        return commandOptions;
    }

    public ArgumentsMetadata getArguments()
    {
        return arguments;
    }

    public List<Accessor> getMetadataInjections()
    {
        return metadataInjections;
    }

    public Class<?> getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("CommandMetadata");
        sb.append("{name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", globalOptions=").append(globalOptions);
        sb.append(", groupOptions=").append(groupOptions);
        sb.append(", commandOptions=").append(commandOptions);
        sb.append(", arguments=").append(arguments);
        sb.append(", metadataInjections=").append(metadataInjections);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public static GuavaUtil.ValueChanger<CommandMetadata, String> nameGetter()
    {
        return new GuavaUtil.ValueChanger<CommandMetadata, String>()
        {
            public String apply(CommandMetadata input)
            {
                return input.getName();
            }
        };
    }
}
