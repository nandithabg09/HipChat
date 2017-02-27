package com.android.hipchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nanditha.gangappa on 1/10/2017.
 */
public class EmotIconsExtractor {
    private static  JSONObject emotIconsObject = null;

    public static JSONObject extractEmotIcons(String query) {
        ArrayList<String> emotIconsArray = new ArrayList<>();
        emotIconsObject = null;

        Pattern p = Pattern.compile("\\([A-Z|a-z|0-9]+\\)*");
        Matcher matcher =  p.matcher(query);
        while (matcher.find()) {
            String match = matcher.group();
            System.out.println(" emotIcon " + match);
            if ((match != null) && (match.length() > 2) && (match.length() < 17) && (emotIconsList.contains(match))) {
                emotIconsArray.add(match.substring(1, match.length()-1));
            }
        }
        for (String s : emotIconsArray) {
            System.out.print(s+" * ");
        }
        if (emotIconsArray.size() > 0) {
            emotIconsObject = new JSONObject();
            try {
                emotIconsObject.put(Constants.sJsonEmotIcons, new JSONArray(emotIconsArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("\n"+emotIconsObject.toString());
            return emotIconsObject;
        } else
            return null;
    }

    private static final String[] emotIcons = new String[] {
            "(allthethings)"       , "(android)"       , "(areyoukiddingme)" , "(arrington)"   , "(arya)"         , "(ashton)"      , "(atlassian)"           , "(awesome)"    ,
            "(awthanks)"           , "(aww)"           , "(awwyiss)"         , "(awyeah)"      , "(badass)"       , "(badjokeeel)"  , "(badpokerface)"        , "(badtime)"    ,
            "(bamboo)"             , "(ban)"           , "(banks)"           , "(basket)"      , "(beer)"         , "(bicepleft)"   , "(bicepright)"          , "(bitbucket)"  ,
            "(boom)"               , "(borat)"         , "(branch)"          , "(bumble)"      , "(bunny)"        , "(cadbury)"     , "(cake)"                , "(cancer)"     ,
            "(candycorn)"          , "(carl)"          , "(caruso)"          , "(catchemall)"  , "(ceilingcat)"   , "(celeryman)"   , "(cereal)"              , "(cerealspit)" ,
            "(challengeaccepted)"  , "(chef)"          , "(chewie)"          , "(chocobunny)"  , "(chompy)"       , "(chucknorris)" , "(clarence)"            , "(coffee)"     ,
            "(confluence)"         , "(content)"       , "(continue)"        , "(cookie)"      , "(cornelius)"    , "(corpsethumb)" , "(crucible)"            , "(daenerys)"   ,
            "(dance)"              , "(dealwithit)"    , "(derp)"            , "(disappear)"   , "(disapproval)"  , "(doge)"        , "(doh)"                 , "(donotwant)"  ,
            "(dosequis)"           , "(downvote)"      , "(drevil)"          , "(drool)"       , "(ducreux)"      , "(dumb)"        , "(dwaboutit)"           , "(evilburns)"  ,
            "(excellent)"          , "(facepalm)"      , "(failed)"          , "(feelsbadman)" , "(feelsgoodman)" , "(finn)"        , "(fireworks)"           , "(fisheye)"    ,
            "(fonzie)"             , "(foreveralone)"  , "(forscale)"        , "(freddie)"     , "(fry)"          , "(ftfy)"        , "(fu)"                  , "(fuckyeah)"   ,
            "(fwp)"                , "(gangnamstyle)"  , "(gates)"           , "(ghost)"       , "(giggity)"      , "(goldstar)"    , "(goodnews)"            , "(greenbeer)"  ,
            "(grumpycat)"          , "(gtfo)"          , "(haha)"            , "(haveaseat)"   , "(heart)"        , "(heygirl)"     , "(hipchat)"             , "(hipster)"    ,
            "(hodor)"              , "(huehue)"        , "(hugefan)"         , "(huh)"         , "(ilied)"        , "(indeed)"      , "(iseewhatyoudidthere)" , "(itsatrap)"   ,
            "(jackie)"             , "(jaime)"         , "(jake)"            , "(jira)"        , "(jobs)"         , "(joffrey)"     , "(jonsnow)"             , "(kennypowers)",
            "(krang)"              , "(kwanzaa)"       , "(lincoln)"         , "(lol)"         , "(lolwut)"       , "(megusta)"     , "(meh)"                 , "(menorah)"    ,
            "(mindblown)"          , "(motherofgod)"   , "(ned)"             , "(nextgendev)"  , "(nice)"         , "(ninja)"       , "(noidea)"              , "(notbad)"     ,
            "(nothingtodohere)"    , "(notit)"         , "(notsureif)"       , "(notsureifgusta)","(obama)"       , "(ohcrap)"      , "(ohgodwhy)"            , "(ohmy)"       ,
            "(okay)"               , "(omg)"           , "(orly)"            , "(paddlin)"       , "(pbr)"        , "(philosoraptor)" , "(pingpong)"          , "(pinkribbon)" ,
            "(pirate)"             , "(pokerface)"     , "(poo)"             , "(present)"       , "(pride)"      , "(pumpkin)"       , "(rageguy)"           , "(rainicorn)"  ,
            "(rebeccablack)"       , "(reddit)"        , "(rockon)"          , "(romney)"        , "(rudolph)"    , "(sadpanda)"      , "(sadtroll)"          , "(salute)"     ,
            "(samuel)"             , "(santa)"         , "(sap)"             , "(scumbag)"       , "(seomoz)"     , "(shamrock)"      , "(shrug)"             , "(skyrim)"     ,
            "(sourcetree)"         , "(standup)"       , "(stare)"           , "(stash)"         , "(success)"    , "(successful)"    , "(sweetjesus)"        , "(tableflip)"  ,
            "(taco)"               , "(taft)"          , "(tea)"             , "(thatthing)"     , "(theyregreat)", "(tree)"          , "(trello)"            , "(trellotaco)" ,
            "(troll)"              , "(truestory)"     , "(trump)"           , "(turkey)"        , "(twss)"       , "(tyrion)"        , "(tywin)"             , "(unacceptable)",
            "(unknown)"            , "(upvote)"        , "(vote)"            , "(waiting)"       , "(washington)" , "(wat)"           , "(whoa)"              , "(whynotboth)"  ,
            "(wtf)"                , "(yey)"           , "(yodawg)"          , "(youdontsay)"    , "(yougotitdude)", "(yuno)"         , "(zoidberg)"
    };
    private static  final ArrayList<String> emotIconsList = new ArrayList<>(Arrays.asList(emotIcons));
}
