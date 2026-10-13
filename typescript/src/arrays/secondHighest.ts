export type Employee = { name: string; department: string; salary: number };

export type Result = Record<string, number | undefined>;

export function findSecondHighestWithMathMax(list: Employee[]): Result {
  return Object.fromEntries(
    [...Map.groupBy(list, (e) => e.department)]
      .sort(([a], [b]) => a.localeCompare(b))
      .map(([dept, emps]) => {
        const salaries = emps.map((e) => e.salary);
        const max = Math.max(...salaries);
        const rest = salaries.filter((s) => s < max); // removes ALL top earners (ties)
        return [dept, rest.length ? Math.max(...rest) : undefined];
      }),
  );
}
