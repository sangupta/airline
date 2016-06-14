package io.airlift.airline;

import io.airlift.airline.guava.GuavaUtil;
import io.airlift.airline.model.CommandGroupMetadata;
import io.airlift.airline.model.CommandMetadata;
import io.airlift.airline.model.GlobalMetadata;
import io.airlift.airline.model.OptionMetadata;

import javax.inject.Inject;

public class GlobalSuggester
        implements Suggester
{
    @Inject
    public GlobalMetadata metadata;

    @Override
    public Iterable<String> suggest()
    {
        return concat(
                GuavaUtil.transform(metadata.getCommandGroups(), CommandGroupMetadata.nameGetter()),
                GuavaUtil.transform(metadata.getDefaultGroupCommands(), CommandMetadata.nameGetter()),
                concat(GuavaUtil.transform(metadata.getOptions(), OptionMetadata.optionsGetter()))
        );
    }
}
