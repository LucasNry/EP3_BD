package com.bdproject.backend.utilities;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.WarConflict;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO {
    public abstract List<Division> retrieveDivision(Division division) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException;

    public abstract List<MilitaryGroup> retrieveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException;

    public abstract List<WarConflict> retrieveWarConflict(WarConflict warConflict) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException;

    public abstract List<PoliticalLeader> retrievePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException;

    public abstract List<MilitaryChief> retrieveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException;

    public abstract void saveDivision(Division division);

    public abstract void saveMilitaryGroup(MilitaryGroup militaryGroup);

    public abstract void saveDivisionIntoMilitaryGroup(String militaryGroupId, Division division);

    public abstract void saveWarConflict(WarConflict warConflict);

    public abstract void savePoliticalLeader(PoliticalLeader politicalLeader);

    public abstract void saveMilitaryChief(MilitaryChief militaryChief);
}
