package com.bdproject.backend.utilities;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.WarConflict;

import java.lang.reflect.InvocationTargetException;
import java.rmi.UnexpectedException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO {
    public abstract List<Division> retrieveDivision(Division division) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException;

    public abstract List<MilitaryGroup> retrieveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;

    public abstract List<WarConflict> retrieveWarConflict(WarConflict warConflict) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;

    public abstract List<PoliticalLeader> retrievePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;

    public abstract List<MilitaryChief> retrieveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;

    public abstract boolean saveDivision(Division division) throws IllegalAccessException;

    public abstract boolean saveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException;

    public abstract boolean saveDivisionIntoMilitaryGroup(Division division, MilitaryGroup militaryGroup) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException, UnexpectedException;

    public abstract boolean saveWarConflict(WarConflict warConflict) throws IllegalAccessException;

    public abstract boolean savePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException;

    public abstract boolean saveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException;

    public abstract boolean updateDivision(Division division) throws IllegalAccessException;
}
