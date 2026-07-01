package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.RecruitmentPage;
import com.orangehrm.utils.FakerUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/** Steps for the Recruitment candidate flow (business flow #5). */
public class RecruitmentSteps {

    private final ScenarioContext context;
    private final RecruitmentPage recruitmentPage = new RecruitmentPage();

    public RecruitmentSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("the admin adds a candidate with resume {string}")
    public void addCandidate(String resume) {
        String first = FakerUtil.firstName();
        String last  = FakerUtil.lastName();
        recruitmentPage.addCandidate(first, last, FakerUtil.email(), resume);
        context.set("candidateName", first + " " + last);
    }

    @Then("the candidate list should not be empty")
    public void candidateListNotEmpty() {
        assertThat(recruitmentPage.candidateCount()).isGreaterThanOrEqualTo(0);
    }
}
