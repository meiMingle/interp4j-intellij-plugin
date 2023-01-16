package dev.khbd.interp4j.intellij.inspection;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellij.codeInsight.intention.IntentionAction;
import dev.khbd.interp4j.intellij.BaseIntellijTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Sergei_Khadanovich
 */
public class StringFormatInspectionTest extends BaseIntellijTest {

    @BeforeMethod
    public void initInspection() {
        fixture.enableInspections(StringFormatMightBeReplacedInspection.class);
    }

    @Test
    public void inspect_simpleStringFormatDetected_warnUsage() {
        fixture.configureByFiles("inspection/format/only_string_conversions/Main.java");

        fixture.testHighlighting(true, false, true);
        List<IntentionAction> intentions = fixture.getAllQuickFixes();
        assertThat(intentions).hasSize(1);
        launchActions(intentions);
        fixture.checkResultByFile("inspection/format/only_string_conversions/Main_after.java");
    }

    @Test
    public void inspect_formatAsStaticField_notHighlight() {
        fixture.configureByFiles("inspection/format/format_as_static_field/Main.java");

        fixture.testHighlighting(true, false, true);
    }

    @Test
    public void inspect_complicatedFormat_notHighlight() {
        fixture.configureByFiles("inspection/format/complicated_format/Main.java");

        fixture.testHighlighting(true, false, true);
    }

    @Test
    public void inspect_withIntegerConversion_warnUsage() {
        fixture.configureByFiles("inspection/format/with_integer_conversion/Main.java");

        fixture.testHighlighting(true, false, true);
        List<IntentionAction> intentions = fixture.getAllQuickFixes();
        assertThat(intentions).hasSize(1);
        launchActions(intentions);
        fixture.checkResultByFile("inspection/format/with_integer_conversion/Main_after.java");
    }

    @Test
    public void inspect_wrongArgumentCount_notHighlight() {
        fixture.configureByFiles("inspection/format/wrong_argument_count/Main.java");

        fixture.testHighlighting(true, false, true);
    }

}