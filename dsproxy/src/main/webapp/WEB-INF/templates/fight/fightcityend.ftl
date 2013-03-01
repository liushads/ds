<#include "/include/header.ftl">
<card title="${game_title}">
<p>
${location}<br/>
太守：战斗结果，
${battle_win_tong}胜利，
幸存${battle_life_num}人，
战死${battle_dead_num}人，
逃跑${battle_away_num}人，
击杀${battle_kill_num}人，
城市福利将在每日凌晨${battle_tax_time}点派发。<br/>
<@gofacility/>
</p>
</card>
</wml>
