package net.skillina.mhshl_android;

/**
 * Created by Alex on 7/19/2016.
 */
public final class LeagueUtils {
    public LeagueUtils(){ }

    public static int resolveTeamLogo(String abbr){
        switch(abbr.toLowerCase()){
            case "qcb": return R.drawable.qcb;
            case "cdr": return R.drawable.cdr;
            case "dbq": return R.drawable.dbq;
            case "wat": return R.drawable.wat;
            case "dmo": return R.drawable.dmo;
            case "dmc": return R.drawable.dmc;
            case "ams": return R.drawable.ams;
            case "mcm": return R.drawable.mcm;
            case "scm": return R.drawable.scm;
            case "ojl": return R.drawable.ojl;
            case "ljs": return R.drawable.ljs;
            case "kcj": return R.drawable.kcj;
            default: return R.drawable.mhshl;
        }
    }

    public static int resolveTeamName(String abbr){
        switch(abbr.toLowerCase()){
            case "qcb": return R.string.name_qcb;
            case "cdr": return R.string.name_cdr;
            case "dbq": return R.string.name_dbq;
            case "wat": return R.string.name_wat;
            case "dmo": return R.string.name_dmo;
            case "dmc": return R.string.name_dmc;
            case "ams": return R.string.name_ams;
            case "mcm": return R.string.name_mcm;
            case "scm": return R.string.name_scm;
            case "ojl": return R.string.name_ojl;
            case "ljs": return R.string.name_ljs;
            case "kcj": return R.string.name_kcj;
            default: return R.string.app_name;
        }
    }
}
