package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.AgentRole;
import com.caredesk.caredesk.model.AgentStatus;
import com.caredesk.caredesk.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/agents") // Group all agent management under this path
public class AdminAgentController {

    @Autowired
    private AgentService agentService;

    // 1. View all agents
    @GetMapping
    public String viewAgentsList(Model model) {
        model.addAttribute("agents", agentService.findAllAgents());
        return "admin/agent-list"; // Path to the Thymeleaf template
    }

    // 2. Show form to add a new agent
    @GetMapping("/new")
    public String showAddAgentForm(Model model) {
        model.addAttribute("agent", new Agent());
        model.addAttribute("roles", AgentRole.values());
        model.addAttribute("statuses", AgentStatus.values());
        return "admin/add-edit-agent";
    }

    // 3. Save a new or updated agent
    @PostMapping("/save")
    public String saveAgent(@ModelAttribute("agent") Agent agent) {
        agentService.saveAgent(agent);
        return "redirect:/admin/agents";
    }

    // 4. Show form to edit an existing agent
    @GetMapping("/edit/{id}")
    public String showEditAgentForm(@PathVariable Long id, Model model) {
        // --- THIS LINE IS CORRECTED ---
        Agent agent = agentService.findAgentById(id);

        model.addAttribute("agent", agent);
        model.addAttribute("roles", AgentRole.values());
        model.addAttribute("statuses", AgentStatus.values());
        return "admin/add-edit-agent";
    }

    // 5. Delete an agent
    @GetMapping("/delete/{id}")
    public String deleteAgent(@PathVariable Long id) {
        agentService.deleteAgentById(id);
        return "redirect:/admin/agents";
    }
}