package io.airlift.airline;

import io.airlift.airline.guava.GuavaUtil;
import io.airlift.airline.model.CommandGroupMetadata;
import io.airlift.airline.model.CommandMetadata;
import io.airlift.airline.model.OptionMetadata;

import javax.inject.Inject;

public class GroupSuggester
        implements Suggester
{
    @Inject
    public CommandGroupMetadata group;

    @Override
    public Iterable<String> suggest()
    {
        return GuavaUtil.concat(
                GuavaUtil.transform(group.getCommands(), CommandMetadata.nameGetter()),
                GuavaUtil.concat(GuavaUtil.transform(group.getOptions(), OptionMetadata.optionsGetter()))
        );
    }
}
