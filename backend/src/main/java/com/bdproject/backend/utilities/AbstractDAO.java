package com.bdproject.backend.utilities;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.WarConflict;

public abstract class AbstractDAO {
    public abstract Division retrieveDivision(Division division);

    public abstract MilitaryGroup retrieveMilitaryGroup(MilitaryGroup militaryGroup);

    public abstract WarConflict retrieveWarConflict(WarConflict warConflict);

    public abstract PoliticalLeader retrievePoliticalLeader(PoliticalLeader politicalLeader);

    public abstract MilitaryChief retrieveMilitaryChief(MilitaryChief militaryChief);

    public abstract void saveDivision(Division division);

    public abstract void saveMilitaryGroup(MilitaryGroup militaryGroup);

    public abstract void saveDivisionIntoMilitaryGroup(String militaryGroupId, Division division);

    public abstract void saveWarConflict(WarConflict warConflict);

    public abstract void savePoliticalLeader(PoliticalLeader politicalLeader);

    public abstract void saveMilitaryChief(MilitaryChief militaryChief);
}
