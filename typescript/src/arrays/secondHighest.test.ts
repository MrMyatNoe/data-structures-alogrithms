import { describe, expect, it } from "vitest";

import { type Employee, findSecondHighestWithMathMax } from "./secondHighest";

const employees: Employee[] = [
  { name: "Alice", department: "Engineering", salary: 120000 },
  { name: "Bob", department: "Engineering", salary: 95000 },
  { name: "Charlie", department: "Engineering", salary: 110000 },
  { name: "Diana", department: "Engineering", salary: 110000 },
  { name: "Eve", department: "HR", salary: 60000 },
  { name: "Frank", department: "HR", salary: 72000 },
  { name: "Grace", department: "HR", salary: 65000 },
  { name: "Henry", department: "Sales", salary: 80000 },
  { name: "Ivy", department: "Sales", salary: 85000 },
  { name: "Jack", department: "Finance", salary: 90000 },
];

describe("secondHighest", () => {
  it.each<{
    name: string;
    input: Employee[];
    expected: Record<string, number | undefined>;
  }>([
    {
      name: "sample data",
      input: employees,
      expected: {
        Engineering: 110000,
        Finance: undefined,
        HR: 65000,
        Sales: 80000,
      },
    },
    { name: "empty input", input: [], expected: {} },
    {
      name: "single employee",
      input: [{ name: "Solo", department: "Ops", salary: 50000 }],
      expected: { Ops: undefined },
    },
    {
      name: "duplicate top salary is not the second highest",
      input: [
        { name: "A", department: "Dev", salary: 100 },
        { name: "B", department: "Dev", salary: 100 },
        { name: "C", department: "Dev", salary: 50 },
      ],
      expected: { Dev: 50 },
    },
    {
      name: "all salaries equal",
      input: [
        { name: "A", department: "Dev", salary: 100 },
        { name: "B", department: "Dev", salary: 100 },
      ],
      expected: { Dev: undefined },
    },
    {
      name: "negative salaries",
      input: [
        { name: "A", department: "Dev", salary: -10 },
        { name: "B", department: "Dev", salary: -30 },
        { name: "C", department: "Dev", salary: -20 },
      ],
      expected: { Dev: -20 },
    },
  ])("$name", ({ input, expected }) => {
    expect(findSecondHighestWithMathMax(input)).toEqual(expected);
  });
});
